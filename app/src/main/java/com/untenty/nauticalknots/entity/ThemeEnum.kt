package com.untenty.nauticalknots.entity

import android.content.Context
import com.untenty.nauticalknots.R

enum class ThemeEnum(private val titleId: Int) {
    SYSTEM(R.string.system_theme),
    DARK(R.string.dark_theme),
    LIGHT(R.string.light_theme);

    fun getTitle(context: Context) =
        context.getString(titleId)
}