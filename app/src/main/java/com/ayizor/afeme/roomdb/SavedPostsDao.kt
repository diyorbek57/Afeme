package com.ayizor.afeme.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ayizor.afeme.roomdb.model.SavedPosts

@Dao
interface SavedPostsDao {
    @Query("SELECT * FROM pin")
    fun getAll(): List<SavedPosts>

    @Query("SELECT * FROM post WHERE id LIKE :id")
    fun findById(id: IntArray): List<SavedPosts>

    @Query("DELETE FROM post")
    fun nukeTable()

    @Insert
    fun insertAll(vararg pin: SavedPosts)

    @Delete
    fun delete(pin: SavedPosts)

}