package com.komshop.data.model

data class CartItem(
    val id: Int?,
    val itemId: String,
    val quantity: Int,
    val name: String,
    val description: String?,
    val currentBid: Double,
    val image: String,
    val media: List<MediaItem>,
    val totalPrice: Double
)