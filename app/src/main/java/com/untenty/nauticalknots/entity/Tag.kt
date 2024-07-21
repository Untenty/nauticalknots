package com.untenty.nauticalknots.entity

import com.untenty.nauticalknots.data.sql.PictureDbEntity
import com.untenty.nauticalknots.data.sql.TagDbEntity

data class Tag(val id: Long, val name: String, val type: TypeTag){

    fun toTagDbEntity(): TagDbEntity = TagDbEntity(
        id = id,
        name = name,
        type = type
    )

}