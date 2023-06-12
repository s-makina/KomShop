package com.komshop.ui.events

import com.komshop.data.model.AuctionItem
import com.komshop.data.model.Product

sealed class BiddingEvents {
    class OnAddToCart(val item: Product): BiddingEvents()
    class OnBidAmountChange(val amount: String): BiddingEvents()
    class OnSubmitBid(val item: AuctionItem): BiddingEvents()
}