package com.komshop.data.model

data class AuctionItem(
    val id: String,
    val name: String,
    val description: String?,
    val currentBid: Double,
    val image: String,
    val media: List<MediaItem>
)