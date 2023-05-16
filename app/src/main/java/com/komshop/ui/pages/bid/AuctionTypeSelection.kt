package com.komshop.ui.pages.bid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.komshop.R
import com.komshop.enam.AuctionTypes
import com.komshop.navigation.Screen
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.TopNav

@Composable
fun SelectAuctionType(navController: NavHostController) {
    PageBackground(
        topBar = {
            TopNav(
                navController = navController,
                title = stringResource(id = R.string.app_name),
            )
        },
    ) {
        LazyColumn {
            item {
                AuctionOption(title = "Daily Sales", icon = R.drawable.ic_coupon) {
                    navController.navigate(Screen.SelectAuction.getRoute(AuctionTypes.CASH_AUCTION))
                }
            }
            item {
                AuctionOption(title = "Online Auctions", icon = R.drawable.ic_internet) {
                    navController.navigate(Screen.DepositRefNumber.route)
                }
            }
            item {
                AuctionOption(title = "Auction Listings", icon = R.drawable.ic_house_auction) {
                    navController.navigate(Screen.DepositRefNumber.route)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuctionOption(title: String, icon: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 16.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
        }
    }
}

