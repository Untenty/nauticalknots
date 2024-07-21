package com.untenty.nauticalknots.data.sql

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbRepository(private val dbDao: DbDao) {
    // Knots
    suspend fun insertNewKnot(knotDbEntity: KnotDbEntity) {
        withContext(Dispatchers.IO) {1
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

    suspend fun updateFavoriteKnotById(id: Long, ord: Long) {
        withContext(Dispatchers.IO) {
            dbDao.updateFavoriteKnotById(id, ord)
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

    suspend fun getTagByID(id: Long): TagInfoTuple {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getTagByID(id)
        }
    }

    suspend fun getTagByName(name: String): TagInfoTuple {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getTagByName(name)
        }
    }

    // Tags knots
    suspend fun insertNewTagKnot(tagKnotDbEntity: TagKnotDbEntity) {
        withContext(Dispatchers.IO) {
            dbDao.insertNewTag(tagKnotDbEntity)
        }
    }

    suspend fun getAllTagsKnots(): List<TagKnotInfoTuple> {
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

    // Pictures

    suspend fun insertNewPicture(pictureDbEntity: PictureDbEntity) {
        withContext(Dispatchers.IO) {
            dbDao.insertNewPicture(pictureDbEntity)
        }
    }

    suspend fun getPicturesKnot(idKnot: Long): List<PictureInfoTuple> {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getPicturesKnot(idKnot)
        }
    }

}