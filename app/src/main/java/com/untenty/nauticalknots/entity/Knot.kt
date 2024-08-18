package com.untenty.nauticalknots.entity

import com.untenty.nauticalknots.data.sql.KnotDbEntity

data class Knot(
    val id: Long,
    val name: String,
    val description: String,
    val tags: MutableList<Tag> = mutableListOf(),
    val pictures: MutableList<String> = mutableListOf()
){
    fun toKnotDbEntity(): KnotDbEntity = KnotDbEntity(
        id = id,
        name = name,
        description = description
    )
}