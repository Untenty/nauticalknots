package com.untenty.nauticalknots.data.sql

import android.content.Context
import androidx.room.Room

object Dependencies {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database1.db").build()
    }

    val dbRepository: DbRepository by lazy { DbRepository(appDatabase.getDao()) }
}