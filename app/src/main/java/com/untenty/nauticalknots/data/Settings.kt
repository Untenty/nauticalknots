package com.untenty.nauticalknots.data

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.untenty.nauticalknots.R
import com.untenty.nauticalknots.entity.LanguageEnum
import com.untenty.nauticalknots.entity.ThemeEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object PreferencesKeys {
    val THEME = stringPreferencesKey("theme")
    val LANGUAGE = stringPreferencesKey("language")
}

object Settings {
    //private var settings: AppSettings = AppSettings(ThemeK.SYSTEM, Language.RU)
    var theme: MutableState<ThemeEnum> = mutableStateOf(ThemeEnum.SYSTEM)
    val language: MutableState<LanguageEnum> = mutableStateOf(LanguageEnum.RU)
    private const val DATASTORE_NAME = "settings"
    private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

    fun init(context: Context) {
        readAppSettings(context)
    }

    fun saveAppSettings(context: Context) {
        // SharedPreference
//        settings.darkModeEnabled = darkModeEnabled
//        val editor = prefs.edit()
//        editor.putString("settings", Json.encodeToString(settings)).apply()
        // DataStore
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.THEME] = theme.value.name
                preferences[PreferencesKeys.LANGUAGE] = language.value.name
            }
            readAppSettings(context)
        }
    }

    private fun readAppSettings(context: Context) {
        // SharedPreference
//        val settingsStr = prefs.getString("settings", "") ?: ""
//        return if (settingsStr != "") {
//            Json.decodeFromString(settingsStr)
//        } else AppSettings("")
        // DataStore
        CoroutineScope(Dispatchers.IO).launch {
            val themeFlow = context.dataStore.data
                .map { preferences ->
                    preferences[PreferencesKeys.THEME] ?: ThemeEnum.SYSTEM.name
                }
            val languageFlow = context.dataStore.data
                .map { preferences ->
                    preferences[PreferencesKeys.LANGUAGE]
                        ?: context.resources.getString(R.string.current_locale)
                }
            theme.value = ThemeEnum.valueOf(themeFlow.first())
            language.value = LanguageEnum.valueOf(languageFlow.first())
        }
    }

}