package com.komshop.data.retrofit.dto

data class AuctionTypeDto(
    val id: String,
    val name: String,
    val image: String,
    val type: String,
    val date_from: String?,
    val date_to: String?
)