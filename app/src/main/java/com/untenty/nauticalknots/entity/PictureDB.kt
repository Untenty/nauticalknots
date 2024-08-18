package com.untenty.nauticalknots.entity

import com.untenty.nauticalknots.data.sql.PictureDbEntity

data class PictureDB(val id: Long, val idKnot: Long, val path: String){

    fun toPictureDbEntity(): PictureDbEntity = PictureDbEntity(
        id = id,
        idKnot = idKnot,
        path = path
    )

}