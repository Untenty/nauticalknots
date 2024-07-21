package com.untenty.nauticalknots.data.sql

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Pictures"
)
data class PictureDbEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "idKnot") val idKnot: Long,
    @ColumnInfo(name = "path") val path: String
)
