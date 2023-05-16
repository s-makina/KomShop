package com.komshop.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user" /*, indices = [Index(value = ["code"], unique = true)]*/)
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val token: String?,
    val last_logged: Boolean = false
)