package com.untenty.nauticalknots.data.sql

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DbDao {
    // Knots
    @Insert(entity = KnotDbEntity::class)
    fun insertNewKnot(knot: KnotDbEntity)

    @Query("SELECT id, name, description FROM Knots ORDER BY name")
    fun getAllKnots(): List<KnotInfoTuple>

    @Query("SELECT id, name, description FROM Knots WHERE id=:id")
    fun getKnot(id: Long): KnotInfoTuple

    // Favorite knots
    @Insert(entity = FavoriteKnotDbEntity::class)
    fun insertNewFavoriteKnot(favoriteKnot: FavoriteKnotDbEntity)

    @Query("SELECT id, ord FROM FavoriteKnots ORDER BY ord, id")
    fun getAllFavoriteKnots(): List<FavoriteKnotInfoTuple>

    @Query("DELETE FROM FavoriteKnots WHERE id = :id")
    fun deleteFavoriteKnotById(id: Long)

    @Query("UPDATE FavoriteKnots SET ord = :ord WHERE id = :id")
    fun updateFavoriteKnotById(id: Long, ord: Long)

    // Tags
    @Insert(entity = TagDbEntity::class)
    fun insertNewTag(tag: TagDbEntity)

    @Query("SELECT id, name, type FROM Tags ORDER BY name")
    fun getAllTags(): List<TagInfoTuple>

    @Query("SELECT id, name, type FROM Tags WHERE id = :id")
    fun getTagByID(id: Long): TagInfoTuple

    @Query("SELECT id, name, type FROM Tags WHERE name = :name")
    fun getTagByName(name: String): TagInfoTuple

    // Tags knots
    @Insert(entity = TagKnotDbEntity::class)
    fun insertNewTag(tagKnot: TagKnotDbEntity)

    @Query("SELECT idTag, idKnot FROM TagsKnots")
    fun getAllTagsKnots(): List<TagKnotInfoTuple>

    @Query("SELECT idTag, idKnot FROM TagsKnots WHERE idKnot = :id")
    fun getAllTagsKnot(id: Long): List<TagKnotInfoTuple>

    @Query("SELECT idTag, idKnot FROM TagsKnots WHERE idTag = :id")
    fun getAllKnotsTag(id: Long): List<TagKnotInfoTuple>

    // Pictures
    @Insert(entity = PictureDbEntity::class)
    fun insertNewPicture(picture: PictureDbEntity)

    @Query("SELECT id, idKnot, path FROM Pictures WHERE idKnot = :idKnot")
    fun getPicturesKnot(idKnot: Long): List<PictureInfoTuple>

}