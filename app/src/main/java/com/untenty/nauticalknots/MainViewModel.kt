package com.untenty.nauticalknots

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.untenty.nauticalknots.data.DataRepository
import com.untenty.nauticalknots.data.sql.Dependencies
import com.untenty.nauticalknots.data.sql.TagKnotInfoTuple
import com.untenty.nauticalknots.entity.FavoriteKnot
import com.untenty.nauticalknots.entity.Knot
import com.untenty.nauticalknots.entity.Tag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.util.Locale

class MainViewModel : ViewModel() {
    var selectedKnot: Knot? = null
    val selectedTabIndex: MutableState<Int> = mutableIntStateOf(0)
    val showHideDescription: MutableState<Boolean> = mutableStateOf(true)

    val searchText = mutableStateOf("")
    val showSearchText = mutableStateOf(false)

    private val listKnotsOfTags: MutableList<Pair<Long, MutableState<List<Long>>>> = mutableListOf()

    val modeSelect: MutableState<Boolean> = mutableStateOf(false)
    val selectedItems: MutableState<List<Long>> = mutableStateOf(listOf())

    var heightScreen = 0
    var widthScreen = 0

    fun getKnots(): MutableState<List<Knot>> {
        return DataRepository.getAllKnots()
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

    fun openKnot(id: Long) {
        selectedKnot = DataRepository.getKnot(id)
    }

    fun getLanguageString(str: String): String {
        return DataRepository.getLanguageString(str)
    }

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    // Knots tags

    /**
     * Initialize the list of knots of tags.
     */
    fun initKnotsOfTags() {
        if (listKnotsOfTags.size == 0) {
            for (tag in DataRepository.getTags().value) {
                listKnotsOfTags.add(Pair(tag.id, mutableStateOf(mutableListOf())))
            }
        }
    }

    fun getKnotsByTag(tag: Tag): MutableState<List<Long>>? {
        return listKnotsOfTags.find { it.first == tag.id }?.second
    }

    fun openCloseKnotsTag(context: LifecycleOwner, tag: Tag) {
        readKnotsByTag(context, tag)
    }

    private fun readKnotsByTag(context: LifecycleOwner, tag: Tag) {
        val list: MutableList<Long> = mutableListOf()
        var liveDataKnotsTags: LiveData<List<TagKnotInfoTuple>>
        if (listKnotsOfTags.find { it.first == tag.id } == null) {
            val listObserver = Observer<List<TagKnotInfoTuple>> { newList ->
                newList.forEach { tagKnot ->
                    list.add(tagKnot.idKnot)
                }
                listKnotsOfTags.add(Pair(tag.id, mutableStateOf(list)))
            }
            CoroutineScope(Dispatchers.IO).launch {
                liveDataKnotsTags = Dependencies.dbRepository.getAllKnotsTag(tag.id)
                withContext(Dispatchers.Main) {
                    liveDataKnotsTags.observe(context, listObserver)
                }
            }
        } else if (listKnotsOfTags.find { it.first == tag.id }?.second?.value?.size == 0) {
            val listObserver = Observer<List<TagKnotInfoTuple>> { newList ->
                newList.forEach { tagKnot ->
                    list.add(tagKnot.idKnot)
                }
                listKnotsOfTags.find { it.first == tag.id }?.second?.value = list
            }
            CoroutineScope(Dispatchers.IO).launch {
                liveDataKnotsTags = Dependencies.dbRepository.getAllKnotsTag(tag.id)
                withContext(Dispatchers.Main) {
                    liveDataKnotsTags.observe(context, listObserver)
                }
            }
        } else {
            listKnotsOfTags.find { it.first == tag.id }?.second?.value = list
        }
    }

    fun modeSelect(status: Boolean) {
        modeSelect.value = status
        selectedItems.value = listOf()
    }

    fun selectItem(id: Long) {
        if (!selectedItems.value.contains(id)) {
            val selectedItemsTemp = selectedItems.value.toMutableList()
            selectedItemsTemp.add(id)
            selectedItems.value = selectedItemsTemp
        }
    }

    fun unselectItem(id: Long) {
        if (selectedItems.value.contains(id)) {
            val selectedItemsTemp = selectedItems.value.toMutableList()
            selectedItemsTemp.remove(id)
            selectedItems.value = selectedItemsTemp
        }
    }

    fun unfavoriteSelectItems() {
        selectedItems.value.forEach { id -> DataRepository.deleteFavoriteKnots(id) }
    }

    fun favoriteSelectItems() {
        selectedItems.value.forEach { id ->
            DataRepository.insertFavoriteKnot(FavoriteKnot(id, DataRepository.getFavoriteKnots().value.size.toLong()))
        }
    }

}