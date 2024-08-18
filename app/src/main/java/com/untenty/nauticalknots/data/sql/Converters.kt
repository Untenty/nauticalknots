package com.untenty.nauticalknots.data.sql

import androidx.room.TypeConverters

@TypeConverters(Converters::class)
class Converters {
//    @TypeConverter
//    fun toTypeTag(value: Int) = enumValues<TypeTag>()[value]
//
//    @TypeConverter
//    fun fromTypeTag(value: TypeTag) = value.ordinal
}