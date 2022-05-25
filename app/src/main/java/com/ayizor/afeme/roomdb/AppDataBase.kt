package com.ayizor.afeme.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayizor.afeme.roomdb.model.SavedPosts

@Database(entities = [SavedPosts::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedPostsDao(): SavedPostsDao
}