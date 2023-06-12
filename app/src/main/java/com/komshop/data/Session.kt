package com.komshop.data

import androidx.compose.runtime.mutableStateOf
import com.komshop.data.model.AuctionItem
import com.komshop.data.model.AuctionType
import com.komshop.data.model.Product
import com.komshop.data.model.User
import com.komshop.ui.theme.orangeBg

object Session {
    val authUser = mutableStateOf<User?>(null)
    val processing = mutableStateOf(false)
    val selectedAuction = mutableStateOf<AuctionType?>(null)
    var currentAuctionItem = mutableStateOf<Product?>(null)
    val statusColor = mutableStateOf(orangeBg)
}