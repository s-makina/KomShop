package com.komshop.ui.events

import com.komshop.data.model.CartItem

sealed class CartEvents {
    object OnLoadItems: CartEvents()
    class RemoveItem(val item: CartItem): CartEvents()
    class ReduceQty(val item: CartItem): CartEvents()
    class AddQty(val item: CartItem): CartEvents()
}