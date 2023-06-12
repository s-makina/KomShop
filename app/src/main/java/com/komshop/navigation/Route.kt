package com.komshop.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.komshop.data.Session
import com.komshop.enam.AuctionTypes
import com.komshop.ui.pages.*
import com.komshop.ui.pages.admin.AdminHome
import com.komshop.ui.pages.admin.ConfigureSMSNotification
import com.komshop.ui.pages.admin.SMSHandler
import com.komshop.ui.pages.bid.AuctionTypeSelection
import com.komshop.ui.pages.bid.DepositRefNumber
import com.komshop.ui.pages.bid.OnlineBidPage
import com.komshop.ui.pages.shop.ProductListPage
import com.komshop.ui.pages.bid.SelectAuctionType
import com.komshop.ui.pages.shop.DailySales
import com.komshop.ui.pages.shop.CartPage
import com.komshop.ui.pages.shop.CheckOutPage
import com.komshop.ui.pages.shop.ProductDetails
import com.komshop.ui.pages.welcome.Login
import com.komshop.ui.pages.welcome.Register
import com.komshop.ui.pages.welcome.Welcome
import com.komshop.ui.viewmodel.BidingViewModel

@Composable
fun Routing(biddingViewModel: BidingViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.AuctionItemSelection.route) {
        composable(route = Screen.Welcome.route) {
            Welcome(navController)
        }

//        composable(route = Screen.PreviewAuctionItem.route) {
//            PreviewAuctionItem(navController)
//        }

        composable(route = Screen.Syncronizing.route) {
            SyncPage(navController)
        }

        composable(route = Screen.Login.route) {
            Login(navController)
        }

        composable(route = Screen.Register.route) {
            Register(navController)
        }

        composable(route = Screen.DepositRefNumber.route) {
            DepositRefNumber(navController)
        }

        composable(
            route = Screen.SelectAuction.route,
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")

            val auctionTypes = AuctionTypes.values().find { type == it.title }
            if (auctionTypes != null)
                AuctionTypeSelection(navController, auctionTypes)
            else {
                Text(text = "Please report this error NO: 001")
            }
        }

        composable(route = Screen.AuctionItemSelection.route) { backStackEntry ->
            ProductListPage(navController, biddingViewModel)
//            val type = backStackEntry.arguments?.getString("type")
//            val auctionTypes = AuctionTypes.values().find { type == it.title }
//            val auctionType = selectedAuction.value
//            if (auctionTypes != null && auctionType != null)
//
//            else {
//                Text(text = "Please report this error NO: 002")
//            }
        }

        composable(route = Screen.AdminHome.route) {
            AdminHome(navController = navController)
        }

        composable(route = Screen.SmsHandler.route) {
            SMSHandler(navController = navController)
        }

        composable(route = Screen.SmsHandler.route) {
            ConfigureSMSNotification(navController = navController)
        }
        composable(route = Screen.OnlineAuction.route) {
            OnlineAuction(navController = navController)
        }

        composable(route = Screen.OnlineBidPage.route) {
            OnlineBidPage(navController = navController)
        }

//        composable(route = Screen.OnlineBidPreview.route) {
//            OnlineAuctionPlaceBid(navController = navController)
//        }
        composable(route = Screen.MyBids.route) {
            ShowMyBids(navController = navController)
        }

        composable(route = Screen.SelectAuctionType.route) {
            SelectAuctionType(navController = navController)
        }

        composable(route = Screen.DailySales.route) {
            DailySales(navController = navController)
        }

        composable(route = Screen.CartPage.route) {
            CartPage(navController = navController)
        }

        composable(route = Screen.CheckOutPage.route) {
            CheckOutPage(navController = navController)
        }

        composable(route = Screen.ProductDetails.route) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            val auctionTypes = AuctionTypes.values().find { type == it.title }
            val product = Session.currentAuctionItem.value
            if (product != null) {
                ProductDetails(navController = navController, product, biddingViewModel)
            } else {
                Text(text = "Please report this error NO: 003")
            }
        }
    }

}