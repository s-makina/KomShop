package com.komshop.util

import com.komshop.enam.AuctionTypes

object ShopUtil {
    fun getPageTitle(auctionTypes: AuctionTypes): String {
        return when (auctionTypes) {
            AuctionTypes.LIVE_AUCTION -> "Prebid"
            AuctionTypes.ONLINE_AUCTION -> "Bid"
            AuctionTypes.CASH_AUCTION -> "Add to Cart"
        }
    }
}