package com.untenty.nauticalknots.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TypeTag(val title: String) {
    @SerialName("PURPOSE")
    PURPOSE("назначение"),
    @SerialName("CATEGORY")
    CATEGORY("категория")
}