package com.komshop.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.navigation.Screen
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.ProgressLoader
import com.komshop.ui.viewmodel.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun SyncPage(navController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()

    LaunchedEffect(key1 = true, block = {
        userViewModel.isUserAuthenticated()
    })
    PageBackground() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            ProgressLoader()
        }
    }

    LaunchedEffect(key1 = userViewModel.state.user, block = {
        delay(3000)
        var page = Screen.AuctionItemSelection.route
//        if (userViewModel.state.user != null) {
//            page = Screen.DailySales.route
//        }
        navController.navigate(page) {
            launchSingleTop = true
        }
    })
}