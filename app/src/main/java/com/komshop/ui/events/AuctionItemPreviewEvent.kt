package com.komshop.ui.events

import com.komshop.data.model.AuctionItem

sealed class AuctionItemPreviewEvent {
    object OnSubmit : AuctionItemPreviewEvent()
    class OnSetCurrentItem(val item: AuctionItem?): AuctionItemPreviewEvent()
    class OnPlaceBid(val amount: String): AuctionItemPreviewEvent()
}