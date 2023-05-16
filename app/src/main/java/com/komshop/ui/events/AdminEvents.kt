package com.komshop.ui.events

sealed class AdminEvents {
    class OnScanUser(val code: String): AdminEvents()
    class OnSubmitBidAmount(val amount: String): AdminEvents()
    object OnAdmitToAuction: AdminEvents()
}