package com.komshop.data.retrofit

import com.komshop.data.model.AuctionItem
import com.komshop.data.model.AuctionType
import com.komshop.data.model.MediaItem
import com.komshop.data.retrofit.dto.AuctionItemDto
import com.komshop.data.retrofit.dto.AuctionTypeDto
import com.komshop.data.retrofit.dto.MediaItemDto

//fun UserDto.toUser(): User {
//    return User(
//        id = id,
//        name = name,
//        email = email,
//        phone = phone,
//        token = token,
//        roles = roles
//    )
//}

fun AuctionTypeDto.toModel(): AuctionType {
    return AuctionType(
        id = id,
        name = name,
        image = image,
        dateFrom = date_from ?: "",
        dateTo = date_to ?: ""
    )
}

fun AuctionItemDto.toModel(): AuctionItem{
    return AuctionItem(
        id = id,
        name = name,
        currentBid = bid_amount,
        description = description,
        image = image,
        media = media.map { it.toMediaItem() }
    )
}

fun MediaItemDto.toMediaItem() = MediaItem(
    original_url
)