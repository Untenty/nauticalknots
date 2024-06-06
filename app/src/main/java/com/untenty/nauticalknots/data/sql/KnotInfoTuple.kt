package com.untenty.nauticalknots.data.sql

import androidx.room.ColumnInfo

data class KnotInfoTuple(
    val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
)
