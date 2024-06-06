package com.untenty.nauticalknots.data.sql

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "FavoriteKnots",
    indices = [Index("id")]
)
data class FavoriteKnotDbEntity(@PrimaryKey val id: Long, val ord: Long)
