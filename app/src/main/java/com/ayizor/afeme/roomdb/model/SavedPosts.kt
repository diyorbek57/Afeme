package com.ayizor.afeme.roomdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedPosts(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "post_id") val pin_id: String?,
    @ColumnInfo(name = "post_url") val pin_url: String?,
    @ColumnInfo(name = "post_board_name") val pin_board: String?
)