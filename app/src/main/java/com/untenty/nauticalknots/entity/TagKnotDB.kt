package com.untenty.nauticalknots.entity

import com.untenty.nauticalknots.data.sql.KnotDbEntity
import com.untenty.nauticalknots.data.sql.TagKnotDbEntity

data class TagKnotDB(val id: Long, val idTag: Long, val idKnot: Long) {
    fun toTagKnotDbEntity(): TagKnotDbEntity = TagKnotDbEntity(
        id = id,
        idTag = idTag,
        idKnot = idKnot
    )
}