package com.komshop.data.room

import com.komshop.data.model.AuctionItem
import com.komshop.data.model.CartItem
import com.komshop.data.room.entity.CartItemEntity

fun AuctionItem.toCartEntity(): CartItemEntity {
    return CartItemEntity(
        id = null,
        itemId = id,
        currentBid = currentBid,
        quantity = 1,
        name = name,
        description = description,
        image = image,
        media = media
    )
}

//fun AuctionItem.toCartItem(): CartItem {
//    return CartItem(
//        id = null,
//        itemId = id,
//        currentBid = currentBid,
//        quantity = 0,
//        name = name,
//        description = description,
//        image = image,
//        media = media
//    )
//}

fun CartItemEntity.toCartItem(): CartItem {
    return CartItem(
        id = id,
        itemId = itemId,
        currentBid = currentBid,
        quantity = quantity,
        name = name,
        description = description,
        image = image,
        media = media,
        totalPrice = quantity * currentBid
    )
}

fun CartItem.toCartEntity(): CartItemEntity {
    return CartItemEntity(
        id = id,
        itemId = itemId,
        currentBid = currentBid,
        quantity = quantity,
        name = name,
        description = description,
        image = image,
        media = media,
    )
}


