package com.komshop.ui.events

import com.komshop.enam.AuctionTypes

sealed class AuctionEvent {
    class OnLoadAuctions(val auctionType: AuctionTypes) : AuctionEvent()
}