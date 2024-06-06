package com.untenty.nauticalknots.entity

import com.untenty.nauticalknots.data.sql.KnotDbEntity

data class Knot(
    val id: Long,
    val name: String,
    val description: String,
    val tags: List<Tag>,
    val pictures: List<String>
){
    fun toKnotDbEntity(): KnotDbEntity = KnotDbEntity(
        id = id,
        name = name,
        description = description
    )
}