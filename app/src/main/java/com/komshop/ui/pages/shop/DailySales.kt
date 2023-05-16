package com.komshop.ui.pages.shop

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.komshop.navigation.Screen
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.TopNav

@Composable
fun DailySales(navController: NavHostController) {
    PageBackground(topBar = { TopNav(navController = navController, title = "Daily Sales")}) {
        ItemListComponent() {
            navController.navigate(Screen.ProductDetails.route)
        }
    }
}