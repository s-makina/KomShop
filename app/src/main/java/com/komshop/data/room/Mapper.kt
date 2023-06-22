package com.komshop.data.room

import com.komshop.data.model.CartItem
import com.komshop.data.model.Product
import com.komshop.data.retrofit.dto.OrderItem
import com.komshop.data.room.entity.ProductEntity
fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = null,
        itemId = id,
        name,
        quantity = 1,
        permalink,
        date_created,
        status,
        featured,
        description,
        shortDescription,
        price,
        regularPrice,
        salePrice,
        on_sale,
        priceHtml,
        stockStatus,
        categories,
        images
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = itemId,
        name,
        permalink,
        date_created,
        status,
        featured,
        description,
        shortDescription,
        price,
        regularPrice,
        salePrice,
        on_sale,
        priceHtml,
        stockStatus,
        categories,
        images
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

fun ProductEntity.toCartItem(): CartItem {
    return CartItem(
        id = id,
        itemId,
        quantity = quantity,
        name,
        permalink,
        date_created,
        status,
        featured,
        description,
        shortDescription,
        price,
        regularPrice,
        salePrice,
        on_sale,
        priceHtml,
        stockStatus,
        categories,
        images
    )
}


fun CartItem.toCartEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        itemId,
        name,
        quantity,
        permalink,
        date_created,
        status,
        featured,
        description,
        shortDescription,
        price,
        regularPrice,
        salePrice,
        on_sale,
        priceHtml,
        stockStatus,
        categories,
        images
    )
}

fun CartItem.toLineItem(): OrderItem {
    return OrderItem(
        product_id = itemId,
        quantity = quantity
    )
}


