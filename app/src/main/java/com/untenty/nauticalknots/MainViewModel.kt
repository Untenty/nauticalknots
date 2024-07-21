package com.untenty.nauticalknots

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.untenty.nauticalknots.data.GenIDImpl
import com.untenty.nauticalknots.data.Repa
import com.untenty.nauticalknots.entity.Knot
import com.untenty.nauticalknots.entity.Tag
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.Locale

class MainViewModel : ViewModel() {
    var selectedElement: Knot? = null
    val selectedTag: MutableState<Tag?> = mutableStateOf(null)
    val selectedTabIndex :MutableState<Int> = mutableIntStateOf(0)
    val showHideDescription :MutableState<Boolean> = mutableStateOf(true)

    var heightScreen = 0
    var widthScreen = 0

    fun getKnots(): MutableState<List<Knot>> {
        return Repa.getAllKnots()
    }

    fun getKnotsByTag(): MutableState<List<Knot>> {
        return Repa.getKnotsByTag()
    }

    fun copyAssetToFile(context: Context, assetFileName: String, outputFileName: String): File? {
        val file = File(context.filesDir, outputFileName)
        try {
            context.assets.open(assetFileName).use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    copyFile(inputStream, outputStream)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return file
    }

    private fun copyFile(inputStream: InputStream, outputStream: FileOutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (inputStream.read(buffer).also { read = it } != -1) {
            outputStream.write(buffer, 0, read)
        }
    }

    fun loadImageFromAssets(context: Context, fileName: String): Bitmap? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun selectElement(id: Long) {
        selectedElement = Repa.getKnot(id)
    }

    fun selectTag(tag: Tag?) {
        selectedTag.value = tag
        Repa.readKnotsByTag(tag)
    }

    fun getLanguageString(str: String): String {
        return Repa.getLanguageString(str)
    }

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

}