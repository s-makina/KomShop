package com.komshop.ui.pages.bid.presentation

import com.komshop.data.model.AuctionType

data class DepositSlipState(
    val currentDepositSlipNumber: String = "",
    val currentDepositSlipNumberError: String? = null,
    val selectedAuctionType: AuctionType? = null,
    val selectedAuctionTypeError: String? = null,
    val otherDeposits: List<String> = emptyList(),
    val auctionTypes: List<AuctionType> = emptyList()
)