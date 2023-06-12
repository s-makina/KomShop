package com.komshop.data.model

import androidx.annotation.Keep

@Keep
data class AuctionType(
    val id: String,
    val name: String,
    val image: String,
    val dateFrom: String,
    val dateTo: String,
)