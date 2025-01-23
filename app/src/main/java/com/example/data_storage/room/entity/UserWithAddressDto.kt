package com.example.data_storage.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithAddressDto(
    @Embedded val user: UserDto,
    @Relation(
        parentColumn = "userId",
        entityColumn = "user_id"
    )
    val addresses: List<AddressDto>
)