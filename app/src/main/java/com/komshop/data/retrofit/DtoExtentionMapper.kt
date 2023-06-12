package com.komshop.data.retrofit

import com.komshop.data.model.AuctionItem
import com.komshop.data.model.AuctionType
import com.komshop.data.model.MediaItem
import com.komshop.data.model.Product
import com.komshop.data.model.ProductCategory
import com.komshop.data.model.ProductImage
import com.komshop.data.retrofit.dto.AuctionItemDto
import com.komshop.data.retrofit.dto.AuctionTypeDto
import com.komshop.data.retrofit.dto.MediaItemDto
import com.komshop.data.retrofit.dto.ProductCategoryDto
import com.komshop.data.retrofit.dto.ProductDto
import com.komshop.data.retrofit.dto.ProductImageDto
import com.komshop.toInt

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

fun AuctionItemDto.toModel(): AuctionItem {
    return AuctionItem(
        id = id,
        name = name,
        currentBid = bid_amount,
        description = description,
        image = image,
        media = media.map { it.toMediaItem() }
    )
}

fun ProductImageDto.toProductImage(): ProductImage {
    return ProductImage(
        src = src
    )
}

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        description = description,
        featured = featured,
        name = name,
        price = toInt(price),
        salePrice = toInt(sale_price),
        priceHtml = price_html,
        regularPrice = toInt(regular_price),
        status = status,
        shortDescription = short_description,
        on_sale = on_sale,
        permalink = permalink,
        date_created = date_created,
        stockStatus = stock_status,
        images = images.map { it.toProductImage() },
        categories = categories.map { it.toProductCategory() }
    )
}

fun ProductCategoryDto.toProductCategory(): ProductCategory {
    return ProductCategory(
        id = id,
        name = name,
        slug = slug
    )
}

fun MediaItemDto.toMediaItem() = MediaItem(
    original_url
)