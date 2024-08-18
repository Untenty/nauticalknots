package com.untenty.nauticalknots.data

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.untenty.nauticalknots.entity.LanguageK
import com.untenty.nauticalknots.entity.ThemeK
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
    var theme: MutableState<ThemeK> = mutableStateOf(ThemeK.SYSTEM)
    var language: MutableState<LanguageK> = mutableStateOf(LanguageK.EN)
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
        val themeFlow = context.dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.THEME] ?: ThemeK.SYSTEM.name
            }
        val languageFlow = context.dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.LANGUAGE] ?: LanguageK.RU.name
            }
        CoroutineScope(Dispatchers.IO).launch {
            theme.value = ThemeK.valueOf(themeFlow.first())
            language.value = LanguageK.valueOf(languageFlow.first())
        }
    }

}