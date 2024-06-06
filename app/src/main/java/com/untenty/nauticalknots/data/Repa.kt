package com.untenty.nauticalknots.data

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.untenty.nauticalknots.data.sql.Dependencies
import com.untenty.nauticalknots.entity.Knot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

object Repa {

    fun init(context: Context) {
        Dependencies.init(context) // room
    }

    fun checkFirstLoad(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            if (Dependencies.dbRepository.getAllKnots().isEmpty()) {
                firstLoad(context)
            }
        }
    }

    private suspend fun firstLoad(context: Context) {
        val outputDir = File(context.filesDir, "unzipped")
        unzipFromAssets(context, "data.zip", outputDir)
        loadJsonToDb(context)
    }

    private suspend fun loadJsonToDb(context: Context) {
        val inputStream = context.assets.open("data.json")
        val reader = InputStreamReader(inputStream)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listType = Types.newParameterizedType(List::class.java, Knot::class.java)
        val jsonAdapter: JsonAdapter<List<Knot>> = moshi.adapter(listType)
        val knots = jsonAdapter.fromJson(reader.readText())
        knots?.forEach {
            Dependencies.dbRepository.insertNewKnot(it.toKnotDbEntity())
        }

    }

    private fun unzipFromAssets(context: Context, assetName: String, outputDir: File) {
        val assetManager = context.assets
        val inputStream = assetManager.open(assetName)
        unzip(inputStream, outputDir)
    }

    private fun unzip(inputStream: InputStream, outputDir: File) {
        ZipInputStream(inputStream).use { zipInputStream ->
            var entry: ZipEntry?
            while (zipInputStream.nextEntry.also { entry = it } != null) {
                val file = File(outputDir, entry!!.name)
                if (entry!!.isDirectory) {
                    file.mkdirs()
                } else {
                    file.parentFile?.mkdirs()
                    FileOutputStream(file).use { output ->
                        zipInputStream.copyTo(output)
                    }
                }
                zipInputStream.closeEntry()
            }
        }
    }

}