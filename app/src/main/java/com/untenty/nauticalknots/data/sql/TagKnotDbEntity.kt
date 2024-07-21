package com.untenty.nauticalknots.data.sql

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "TagsKnots",
    indices = [Index("idTag"), Index("idKnot")]
)
data class TagKnotDbEntity(
    @PrimaryKey val id: Long,
    val idTag: Long,
    val idKnot: Long,
)
