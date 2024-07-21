package com.untenty.nauticalknots.data.sql

import androidx.room.ColumnInfo

data class PictureInfoTuple(
    val id: Long,
    @ColumnInfo(name = "idKnot") val idKnot: Long,
    @ColumnInfo(name = "path") val path: String
)
