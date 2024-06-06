package com.untenty.nauticalknots.data.sql

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.untenty.nauticalknots.entity.TypeTag

@Entity(
    tableName = "Tags",
    indices = [Index("id")]
)
data class TagDbEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: TypeTag
)