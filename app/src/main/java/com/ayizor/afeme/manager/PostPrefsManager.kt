package com.ayizor.afeme.manager

import android.content.Context
import android.content.SharedPreferences

class PostPrefsManager(context: Context) {
    val postSharedPreferences: SharedPreferences?

    init {

        postSharedPreferences = context.getSharedPreferences("post_db", Context.MODE_PRIVATE)
    }

    fun storePostType(id: String?) {
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putString("post_type", id)
        prefsEditor.apply()
    }

    fun loadPostType(): String? {
        return postSharedPreferences!!.getString("post_type", "")
    }

    fun storeBuildingType(id: String?) {
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putString("building_type", id)
        prefsEditor.apply()
    }

    fun loadBuildingType(): String? {
        return postSharedPreferences!!.getString("building_type", "")
    }
}