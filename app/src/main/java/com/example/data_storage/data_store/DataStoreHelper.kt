package com.example.data_storage.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.data_storage.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.first


class DataStoreHelper(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    private val gson = Gson()

    suspend fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        context.dataStore.edit { preferences ->
            preferences[USER_KEY] = userJson
        }
    }

    suspend fun getUser(): User? {
        val preferences = context.dataStore.data.first()
        val userJson = preferences[USER_KEY]
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            User()
        }
    }

    private companion object {
        const val DATA_STORE_NAME = "user_prefs"
        const val USER_KEY_NAME = "user_key"
        val USER_KEY = stringPreferencesKey(USER_KEY_NAME)
    }
}