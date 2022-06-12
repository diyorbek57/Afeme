package com.ayizor.afeme.manager

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {
    val sharedPreferences: SharedPreferences?


    init {
        sharedPreferences = context.getSharedPreferences("afeme_db", Context.MODE_PRIVATE)

    }

    fun storeDeviceToken(token: String?) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putString("device_token", token)
        prefsEditor.apply()
    }

    fun loadDeviceToken(): String? {
        return sharedPreferences!!.getString("device_token", "")
    }


    fun storeAuthorizationToken(token: String?) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putString("auth_token", token)
        prefsEditor.apply()
    }

    fun loadAuthorizationToken(): String? {
        return sharedPreferences!!.getString("auth_token", "")
    }


    fun storeUserId(id: String?) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putString("user_id", id)
        prefsEditor.apply()
    }

    fun loadUserId(): String? {
        return sharedPreferences!!.getString("user_id", "")
    }


    fun storeUserRegistered(id: Boolean) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putBoolean("user_registered", id)
        prefsEditor.apply()
    }

    fun loadUserRegistered(): Boolean {
        return sharedPreferences!!.getBoolean("user_registered", false)
    }

    fun storeUserRegisteredToken(token: String) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putString("user_registered_token", token)
        prefsEditor.apply()
    }

    fun loadUserRegisteredToken(): String? {
        return sharedPreferences!!.getString("user_registered_token", "")
    }


}