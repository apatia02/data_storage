package com.example.data_storage.shared

import android.content.Context
import android.content.SharedPreferences
import com.example.data_storage.model.User
import com.google.gson.Gson

class SharedPreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        val editor = sharedPreferences.edit()
        val userJson = Gson().toJson(user)
        editor.putString(USER_KEY, userJson)
        editor.apply()
    }

    fun getUser(): User {
        val userJson = sharedPreferences.getString(USER_KEY, null)
        return if (userJson != null) {
            Gson().fromJson(userJson, User::class.java)
        } else {
            User()
        }
    }

    fun clearShared() {
        val editor = sharedPreferences.edit()
        editor.putString(USER_KEY, null)
        editor.apply()
    }

    private companion object {
        const val SHARED_KEY = "shared_preferences"
        const val USER_KEY = "user_key"
    }
}