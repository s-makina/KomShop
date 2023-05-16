package com.komshop.data.retrofit.dto

import androidx.annotation.Keep
import com.komshop.data.model.Role


@Keep
class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val token: String?,
    val code: String,
    val roles: List<Role>
)


