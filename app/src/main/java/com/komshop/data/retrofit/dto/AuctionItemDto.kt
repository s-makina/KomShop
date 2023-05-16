package com.komshop.data.retrofit.dto

data class AuctionItemDto(
    val id: String,
    val name: String,
    val bid_amount: Double,
    val description: String?,
    val image: String,
    val media: List<MediaItemDto>
)