package com.untenty.nauticalknots.data

import android.content.SharedPreferences
import com.untenty.nauticalknots.entity.AppSettings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Settings {
    private lateinit var prefs: SharedPreferences
    private var settings: AppSettings = AppSettings("")

    fun init(sharedPreferences: SharedPreferences) {
        prefs = sharedPreferences
        settings = readAppSettings()
    }

    fun saveAppSettings(ip: String) {
        settings.url = ip
        val editor = prefs.edit()
        editor.putString("settings", Json.encodeToString(settings)).apply()
    }

    fun readAppSettings(): AppSettings {
        val settingsStr = prefs.getString("settings", "") ?: ""
        return if (settingsStr != "") {
            Json.decodeFromString(settingsStr)
        } else AppSettings("")
    }

    fun getAppSettings(): AppSettings {
        return settings
    }
}