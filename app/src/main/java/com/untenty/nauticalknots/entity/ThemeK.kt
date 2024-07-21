package com.untenty.nauticalknots.entity

import android.content.Context
import androidx.compose.ui.res.stringResource
import com.untenty.nauticalknots.R

enum class ThemeK(val titleId: Int) {
    SYSTEM(R.string.system_theme),
    DARK(R.string.dark_theme),
    LIGHT(R.string.light_theme);

    fun getTitle(context: Context) =
        context.getString(titleId)
}