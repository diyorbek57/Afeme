package com.ayizor.afeme.manager

import android.content.Context
import android.content.SharedPreferences

class UserPrefsManager(context: Context) {

    val userSharedPreferences: SharedPreferences?


    init {
        userSharedPreferences = context.getSharedPreferences("user_db", Context.MODE_PRIVATE)

    }
    fun clearSavedPostDatas() {
        userSharedPreferences?.edit()?.clear()?.commit()
    }
}