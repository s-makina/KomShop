package com.komshop.ui.events

import com.komshop.data.model.AuctionItem

sealed class ProductListEvents {
    class OnBid(val auctionItem: AuctionItem): ProductListEvents()
    class OnSearch(val searchTerm: String): ProductListEvents()

    class OnFilterCategory(val id: String): ProductListEvents()
    object SubmitSearch: ProductListEvents()
    object OnLoadItems: ProductListEvents()
    object OnLoadMyBids: ProductListEvents()
    object OnLoadCategories: ProductListEvents()
}
