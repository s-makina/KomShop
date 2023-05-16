package com.komshop.ui.events

sealed class SaleEvents {
    object OnLoadData: SaleEvents()
    object OnLoadAuctionTypes: SaleEvents()
}