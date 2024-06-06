package com.untenty.nauticalknots.data.sql

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.untenty.nauticalknots.entity.TypeTag

@TypeConverters(Converters::class)
class Converters {
//    @TypeConverter
//    fun toTypeTag(value: Int) = enumValues<TypeTag>()[value]
//
//    @TypeConverter
//    fun fromTypeTag(value: TypeTag) = value.ordinal
}