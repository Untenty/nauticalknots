package com.untenty.nauticalknots.data.sql

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 3,
    entities = [
        KnotDbEntity::class,
        TagDbEntity::class,
        TagKnotDbEntity::class,
        FavoriteKnotDbEntity::class,
        PictureDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDao(): DbDao

}