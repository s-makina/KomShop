package com.komshop.data.model

import androidx.annotation.Keep

@Keep
data class CartItem(
    val id: Int?,
    val itemId: String,
    val quantity: Int,
    val name: String,
    val permalink: String,
    val date_created: String,
    val status: String,
    val featured: Boolean,
    val description: String,
    val shortDescription: String,
    val price: Int,
    val regularPrice: Int,
    val salePrice: Int,
    val on_sale: Boolean,
    val priceHtml: String,
    val stockStatus: String,
    val categories: List<ProductCategory>,
    val images: List<ProductImage>,
//    val media: List<MediaItem>,
//    val totalPrice: Double
)