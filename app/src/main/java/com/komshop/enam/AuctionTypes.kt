package com.komshop.enam

enum class AuctionTypes(val title: String, val pageTitle: String) {
    LIVE_AUCTION("live", "Live Auctions"),
    ONLINE_AUCTION("online", "Online Auctions"),
    CASH_AUCTION("cash", "Cash Auctions")
}