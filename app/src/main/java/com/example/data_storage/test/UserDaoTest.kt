package com.example.data_storage.test

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.data_storage.room.AppDatabase
import com.example.data_storage.room.UserDao
import com.example.data_storage.room.entity.AddressDto
import com.example.data_storage.room.entity.GeoDto
import com.example.data_storage.room.entity.UserDto
import com.example.data_storage.room.entity.UserWithAddressDto

class UserDaoTest(context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "user_database"
    ).build()

    private val userDao: UserDao = db.userDao()

    fun runTest() {
        // 1. Вставка нового пользователя
        val user = UserDto(name = "John Doe", email = "john.doe@example.com")
        userDao.insertUser(user)
        Log.d(TAG, "After inserting user: ${getAllUsers()}")

        // 2. Вставка адреса для пользователя с географическими данными
        val geo = GeoDto(lat = 40.7128, lng = -74.0060, country = "USA")
        val address = AddressDto(
            userId = user.userId,
            street = "Main Street",
            city = "New York",
            zipcode = "10001",
            geo = geo
        )
        userDao.insertAddress(address)
        Log.d(TAG, "After inserting address with geo: ${getAllUsersWithAddresses()}")

        // 3. Получение пользователя по ID
        val retrievedUser = userDao.getUserById(user.userId)
        Log.d(TAG, "Retrieved user: ${retrievedUser?.name}, ${retrievedUser?.email}")

        // 4. Получение всех адресов пользователя с географией
        val userAddresses = userDao.getAddressesForUser(user.userId)
        Log.d(
            TAG,
            "User addresses with geo: ${userAddresses.joinToString { it.city + " (Lat: ${it.geo.lat}, Lng: ${it.geo.lng}, Country: ${it.geo.country})" }}"
        )

        // 5. Обновление данных пользователя
        val updatedUser = retrievedUser?.copy(name = "Jane Doe")
        if (updatedUser != null) {
            userDao.updateUser(updatedUser)
        }
        Log.d(TAG, "After updating user: ${getAllUsers()}")

        // 6. Получение пользователя с его адресами (составной объект)
        val userWithAddresses = userDao.getUserWithAddresses(user.userId)
        Log.d(
            TAG,
            "User with addresses: ${userWithAddresses.user.name}, ${userWithAddresses.addresses.joinToString { it.city + " (Lat: ${it.geo.lat}, Lng: ${it.geo.lng}, Country: ${it.geo.country})" }}"
        )

        // 7. Обновление адреса
        val updatedAddress = address.copy(street = "Updated Street")
        userDao.updateAddress(updatedAddress)
        Log.d(TAG, "After updating address: ${getAllUsersWithAddresses()}")

        // 8. Удаление адреса
        userDao.deleteAddress(updatedAddress)
        Log.d(TAG, "After deleting address: ${getAllUsersWithAddresses()}")

        // 9. Удаление пользователя
        updatedUser?.let { userDao.deleteUser(it) }
        Log.d(TAG, "After deleting user: ${getAllUsers()}")
    }

    // Получение всех пользователей
    private fun getAllUsers(): List<UserDto> {
        return userDao.getAllUsers()
    }

    // Получение всех пользователей с адресами
    private fun getAllUsersWithAddresses(): List<UserWithAddressDto> {
        return userDao.getAllUsersWithAddresses()
    }

    private companion object {
        const val TAG = "UserDaoTest"
    }
}