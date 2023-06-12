package com.komshop.data

import com.komshop.data.model.CartItem
import com.komshop.data.model.User
import com.komshop.data.retrofit.dto.UserDto
import com.komshop.data.room.entity.UserEntity

fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        phone = phone,
        token = token,
        roles = roles
    )
}

fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        phone = phone,
        token = token,
        roles = emptyList()
    )
}

fun UserDto.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        phone = phone,
        token = token,
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        phone = phone,
        token = token,
    )
}

fun CartItem.getTotalPrice(): Double {
    return (price * quantity).toDouble()
}