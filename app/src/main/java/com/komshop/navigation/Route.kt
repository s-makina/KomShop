package com.komshop.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.komshop.data.Session
import com.komshop.enam.AuctionTypes
import com.komshop.ui.pages.*
import com.komshop.ui.pages.shop.ProductListPage
import com.komshop.ui.pages.shop.CartPage
import com.komshop.ui.pages.shop.CheckOutPage
import com.komshop.ui.pages.shop.ProductDetails
import com.komshop.ui.viewmodel.BidingViewModel

@Composable
fun Routing(biddingViewModel: BidingViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.AuctionItemSelection.route) {

        composable(route = Screen.AuctionItemSelection.route) { backStackEntry ->
            ProductListPage(navController, biddingViewModel)
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

        composable(route = Screen.AboutUs.route) {
            AboutUs(navController)
        }
    }

}