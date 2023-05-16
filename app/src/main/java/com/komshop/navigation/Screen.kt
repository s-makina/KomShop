package com.komshop.navigation

import com.komshop.enam.AuctionTypes

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object PreviewAuctionItem : Screen("preview_auction_item")
    object Welcome : Screen("welcome_screen")
    object DepositRefNumber : Screen("deposit_ref_screen")
    object SelectAuction : Screen("select_auction_screen/{type}") {
        fun getRoute(auctionTypes: AuctionTypes) =
            "select_auction_screen/${auctionTypes.title}"
    }

    object TagItems : Screen("tag_items_screen")
    object ItemStatus : Screen("change_status_screen")
    object DeliveryItems : Screen("delivery_items_screen")
    object AcceptDelivery : Screen("accept_delivery_screen")
    object AuctionItemSelection : Screen("auction_item_selection_screen/{type}") {
        fun getRoute(auctionTypes: AuctionTypes) =
            "auction_item_selection_screen/${auctionTypes.title}"
    }
    object Syncronizing : Screen("syncronizing_screen")
    object AdminHome : Screen("admin_home_screen")
    object SmsHandler : Screen("sms_handler_screen")
    object OnlineAuction : Screen("online_auction_screen")
    object OnlineBidPage : Screen("online_bid_page_screen")
    object OnlineBidPreview : Screen("online_bid_preview_screen")
    object MyBids : Screen("my_bids_screen")
    object ConfigureSms : Screen("configure_sms_screen")
    object SelectAuctionType : Screen("select_auction_type")
    object DailySales : Screen("daily_sales")
    object CartPage : Screen("cart_page")
    object CheckOutPage : Screen("check_out_page")
    object ProductDetails : Screen("product_details_page/{type}"){
        fun getRoute(auctionTypes: AuctionTypes) =
            "product_details_page/${auctionTypes.title}"
    }
}