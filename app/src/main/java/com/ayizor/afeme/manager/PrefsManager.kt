package com.ayizor.afeme.manager

import android.content.Context
import android.content.SharedPreferences
import com.ayizor.afeme.model.User
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
    fun storeUserCurrentLocation(location: LatLng) {
        val gson = Gson()
        val json = gson.toJson(location)
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putString("current_location", json)
        prefsEditor.apply()
    }
    fun loadUserCurrentLocation(): LatLng {
        val gson = Gson()
        val json: String? = sharedPreferences?.getString("current_location", null)
        val type = object : TypeToken<LatLng>() {}.type
        return gson.fromJson(json, type)
    }

}