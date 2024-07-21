package com.untenty.nauticalknots.entity

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(var theme: ThemeK, var language: LanguageK)