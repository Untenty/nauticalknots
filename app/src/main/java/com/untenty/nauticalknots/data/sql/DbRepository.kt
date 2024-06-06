package com.untenty.nauticalknots.data.sql

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbRepository(private val dbDao: DbDao) {
    // Knots
    suspend fun insertNewKnot(knotDbEntity: KnotDbEntity) {
        withContext(Dispatchers.IO) {
            dbDao.insertNewKnot(knotDbEntity)
        }
    }

    suspend fun getAllKnots(): List<KnotInfoTuple> {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getAllKnots()
        }
    }

    suspend fun getKnot(id: Long): KnotInfoTuple {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getKnot(id)
        }
    }

    // Favorite knots
    suspend fun insertNewFavoriteKnot(favoriteKnotDbEntity: FavoriteKnotDbEntity) {
        withContext(Dispatchers.IO) {
            dbDao.insertNewFavoriteKnot(favoriteKnotDbEntity)
        }
    }

    suspend fun getAllFavoriteKnot(): List<FavoriteKnotInfoTuple> {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getAllFavoriteKnots()
        }
    }

    suspend fun deleteFavoriteKnotById(id: Long) {
        withContext(Dispatchers.IO) {
            dbDao.deleteFavoriteKnotById(id)
        }
    }

    // Tags
    suspend fun insertNewTag(tagDbEntity: TagDbEntity) {
        withContext(Dispatchers.IO) {
            dbDao.insertNewTag(tagDbEntity)
        }
    }

    suspend fun getAllTags(): List<TagInfoTuple> {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getAllTags()
        }
    }

    // Tags knots
    suspend fun insertNewTagKnot(tagKnotDbEntity: TagKnotDbEntity) {
        withContext(Dispatchers.IO) {
            dbDao.insertNewTag(tagKnotDbEntity)
        }
    }

    suspend fun getAllTagsKnots(id: Long): List<TagKnotInfoTuple> {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getAllTagsKnots()
        }
    }

    suspend fun getAllTagsKnot(id: Long): List<TagKnotInfoTuple> {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getAllTagsKnot(id)
        }
    }

    suspend fun getAllKnotsTag(id: Long): List<TagKnotInfoTuple> {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getAllKnotsTag(id)
        }
    }

}