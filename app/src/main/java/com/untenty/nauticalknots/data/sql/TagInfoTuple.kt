package com.untenty.nauticalknots.data.sql

import androidx.room.ColumnInfo
import com.untenty.nauticalknots.entity.TypeTag

data class TagInfoTuple(
    val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: TypeTag
)
