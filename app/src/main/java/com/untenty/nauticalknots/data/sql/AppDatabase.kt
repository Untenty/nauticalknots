package com.untenty.nauticalknots.data.sql

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        KnotDbEntity::class,
        TagDbEntity::class,
        TagKnotDbEntity::class,
        FavoriteKnotDbEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDao(): DbDao

}