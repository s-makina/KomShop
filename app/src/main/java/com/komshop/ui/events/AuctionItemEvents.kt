package com.komshop.ui.events

import com.komshop.data.model.AuctionItem

sealed class AuctionItemEvents {
    class OnBid(val auctionItem: AuctionItem): AuctionItemEvents()
    object OnLoadItems: AuctionItemEvents()
    object OnLoadMyBids: AuctionItemEvents()
}
