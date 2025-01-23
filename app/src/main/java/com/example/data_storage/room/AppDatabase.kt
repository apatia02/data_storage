package com.example.data_storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_storage.room.entity.AddressDto
import com.example.data_storage.room.entity.UserDto

@Database(entities = [UserDto::class, AddressDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}