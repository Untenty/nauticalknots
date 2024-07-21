package com.untenty.nauticalknots.entity

import com.untenty.nauticalknots.data.sql.FavoriteKnotDbEntity

data class FavoriteKnot(val id: Long, val ord: Long) {
    fun toFavoriteKnotDbEntity(): FavoriteKnotDbEntity = FavoriteKnotDbEntity(
        id = id,
        ord = ord
    )
}
