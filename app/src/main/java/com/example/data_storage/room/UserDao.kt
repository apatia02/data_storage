package com.example.data_storage.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data_storage.room.entity.AddressDto
import com.example.data_storage.room.entity.UserDto
import com.example.data_storage.room.entity.UserWithAddressDto

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun insertUser(user: UserDto)

    @Insert(onConflict = REPLACE)
    fun insertAddress(address: AddressDto)

    @Query("SELECT * FROM users WHERE userId = :id")
    fun getUserById(id: Long): UserDto?

    @Query("SELECT * FROM addresses WHERE user_id = :userId")
    fun getAddressesForUser(userId: Long): List<AddressDto>

    @Update
    fun updateUser(user: UserDto)

    @Update
    fun updateAddress(address: AddressDto)

    @Delete
    fun deleteUser(user: UserDto)

    @Delete
    fun deleteAddress(address: AddressDto)

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserWithAddresses(userId: Long): UserWithAddressDto

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<UserDto>

    @Query("SELECT * FROM users")
    fun getAllUsersWithAddresses(): List<UserWithAddressDto>
}