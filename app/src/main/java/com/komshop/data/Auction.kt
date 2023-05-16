package com.komshop.data

import androidx.annotation.DrawableRes


data class Auction(
    val title: String,
    @DrawableRes val icon:  Int
)