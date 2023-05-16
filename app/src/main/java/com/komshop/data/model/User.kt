package com.komshop.data.model

import androidx.annotation.Keep

@Keep
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val token: String?,
    val roles: List<Role>
)