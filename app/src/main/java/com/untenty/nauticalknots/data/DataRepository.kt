package com.untenty.nauticalknots.data

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.untenty.nauticalknots.data.sql.Dependencies
import com.untenty.nauticalknots.entity.FavoriteKnot
import com.untenty.nauticalknots.entity.Knot
import com.untenty.nauticalknots.entity.PictureDB
import com.untenty.nauticalknots.entity.Tag
import com.untenty.nauticalknots.entity.TagKnotDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Locale
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

object DataRepository {

    private const val DATA_FILE_ZIP = "data.zip"
    private const val DATA_FILE = "data.json"
    private var listKnots: MutableState<List<Knot>> = mutableStateOf(listOf())
    private var listTags: MutableState<List<Tag>> = mutableStateOf(listOf())
    private var favoriteKnots: MutableState<List<FavoriteKnot>> = mutableStateOf(listOf())

    fun init(context: Context) {
        Settings.init(context)
        Dependencies.init(context) // room
        CoroutineScope(Dispatchers.IO).launch {
            val jobCheck = checkFirstLoad(context)
            jobCheck.join()
            readKnots()
            readTags()
            readFavoriteKnots()
        }
    }

    private fun checkFirstLoad(context: Context): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            if (Dependencies.dbRepository.getAllKnots().isEmpty()) {
                firstLoad(context)
            }
        }
    }

    private suspend fun firstLoad(context: Context) {
        val outputDir = File(context.filesDir, "unzipped")
        unzipFromAssets(context, outputDir)
        loadJsonToDb(context)
    }

    private suspend fun loadJsonToDb(context: Context) {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listType = Types.newParameterizedType(List::class.java, Knot::class.java)
        val jsonAdapter: JsonAdapter<List<Knot>> = moshi.adapter(listType)
//        val inputStream = context.assets.open(DATA_FILE)
        val inputStream = File(context.filesDir.path + "/unzipped/" + DATA_FILE).inputStream()
        val reader = InputStreamReader(inputStream)
        val knots = jsonAdapter.fromJson(reader.readText())
// +Dagger
//        val appComponent = DaggerAppComponent.create(context)
//        val appComponent2 = AppComponent.create(context)
//        appComponent.getListKnotsWrapper(context)
//        val knots = appComponent.getKnots()
// -Dagger
        var idTagKnot = 0L
        var idPicture = 0L
        var idTag = 0L
        knots?.forEach {
            Dependencies.dbRepository.insertNewKnot(it.toKnotDbEntity())
            for (tag in it.tags) {
                @Suppress("UNNECESSARY_SAFE_CALL") // room ever return type?
                var idCurTag = Dependencies.dbRepository.getTagByName(tag.name)?.id
                if (idCurTag == null) {
                    idCurTag = idTag
                    Dependencies.dbRepository.insertNewTag(
                        Tag(idTag++, tag.name, tag.type).toTagDbEntity()
                    )
                }
                Dependencies.dbRepository.insertNewTagKnot(
                    TagKnotDB(
                        idTagKnot++,
                        idCurTag,
                        it.id
                    ).toTagKnotDbEntity()
                )
            }
            for (pic in it.pictures) {
                Dependencies.dbRepository.insertNewPicture(
                    PictureDB(
                        idPicture++,
                        it.id,
                        pic
                    ).toPictureDbEntity()
                )
            }
        }
    }

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
//        val config2 = Configuration(context.resources.configuration)
//        config2.setLocale(Locale("en")) // Set locale to English, for example
//        val newContext = context.createConfigurationContext(config2)
    }

    private fun unzipFromAssets(context: Context, outputDir: File) {
        val assetManager = context.assets
        val inputStream = assetManager.open(DATA_FILE_ZIP)
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

    fun readKnots(searchText: String = "") {
        CoroutineScope(Dispatchers.IO).launch {
            val listKnotsTemp: MutableList<Knot> = mutableListOf()
            for (knotDB in Dependencies.dbRepository.getAllKnots(searchText)) {
                val newKnot = Knot(knotDB.id, knotDB.name, knotDB.description)
                for (tagDB in Dependencies.dbRepository.getAllTagsKnot(knotDB.id)) {
                    val tag = Dependencies.dbRepository.getTagByID(tagDB.idTag)
                    newKnot.tags.add(Tag(tagDB.idTag, tag.name, tag.type))
                }
                for (picDB in Dependencies.dbRepository.getPicturesKnot(knotDB.id)) {
                    newKnot.pictures.add(picDB.path)
                }
                listKnotsTemp.add(newKnot)
            }
            listKnotsTemp.sortBy { getLanguageString(it.name) }
            listKnots.value = listKnotsTemp.toList()
        }
    }

    fun getAllKnots(): MutableState<List<Knot>> {
        return listKnots
    }

    fun getKnot(id: Long): Knot? {
        return listKnots.value.find { el -> el.id == id }
    }

    fun getLanguageString(str: String): String {
        val lang = Settings.language.value.name
        val regex =
            Regex("${lang}='([^']*)'", setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE))
        val matchResult = regex.find(str)
        return matchResult?.groupValues?.last() ?: ""
    }

    fun getTags(): MutableState<List<Tag>> {
        return listTags
    }

    private fun readTags() {
        CoroutineScope(Dispatchers.IO).launch {
            val listTagsL: MutableList<Tag> = mutableListOf()
            Dependencies.dbRepository.getAllTags().forEach {
                listTagsL.add(Tag(it.id, it.name, it.type))
            }
            listTags.value = listTagsL
        }
    }

    fun insertFavoriteKnot(favoriteKnot: FavoriteKnot) {
        CoroutineScope(Dispatchers.IO).launch {
            if (favoriteKnots.value.find { it.id == favoriteKnot.id } == null) {
                Dependencies.dbRepository.insertNewFavoriteKnot(favoriteKnot.toFavoriteKnotDbEntity())
                readFavoriteKnots()
            }
        }
    }

    fun deleteFavoriteKnots(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            Dependencies.dbRepository.deleteFavoriteKnotById(id)
            renumberingFavoriteKnots()
            readFavoriteKnots()
        }
    }

    private fun renumberingFavoriteKnots() {
        CoroutineScope(Dispatchers.IO).launch {
            var ord = 0L
            favoriteKnots.value.forEach {
                Dependencies.dbRepository.updateFavoriteKnotById(it.id, ord++)
            }
        }
    }

    private fun readFavoriteKnots() {
        CoroutineScope(Dispatchers.IO).launch {
            val favoriteKnotsTemp: MutableList<FavoriteKnot> = mutableListOf()
            Dependencies.dbRepository.getAllFavoriteKnot().forEach { knot ->
                favoriteKnotsTemp.add(FavoriteKnot(knot.id, knot.ord))
            }
            favoriteKnotsTemp.sortBy { it.ord }
            favoriteKnots.value = favoriteKnotsTemp
        }
    }

    fun getFavoriteKnots(): MutableState<List<FavoriteKnot>> {
        return favoriteKnots
    }

    fun isKnotFavorite(knot: Knot): Boolean {
        return favoriteKnots.value.find { it.id == knot.id } != null
    }

}