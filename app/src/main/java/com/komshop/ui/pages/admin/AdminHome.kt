package com.komshop.ui.pages.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.R
import com.komshop.ui.componets.TopNav
import com.komshop.ui.dialog.AdmitToAuction
import com.komshop.ui.dialog.RegisterWinnerDialog
import com.komshop.ui.dialog.ScannerDialog
import com.komshop.ui.events.AdminEvents
import com.komshop.ui.viewmodel.AdminHomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHome(navController: NavHostController) {
    val adminHomeViewModel: AdminHomeViewModel = hiltViewModel()
    val state = adminHomeViewModel.state


    val showScannerDialog = remember { mutableStateOf(false) }
    val showUserDialog = remember { mutableStateOf(false) }
    val showWinnerDialog = remember { mutableStateOf(false) }
    var scanAction by remember { mutableStateOf("Admit To Auction") }
    var isWinner by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopNav(
                title = "Admin Home",
                navController = navController,
//                subtitle = "Find your registered auctions"
            )
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = { Text("") }, ) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painterResource(id = R.drawable.ic_decal2),
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.BottomEnd
                ),
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                item {
                    AuctionItem(title = "Admit to auction", R.drawable.ic_user_allow) {
                        scanAction = "Admit To Auction"
                        isWinner = false
                        showScannerDialog.value = true
                    }
                }

                item {
                    AuctionItem(title = "Register Winner", R.drawable.ic_medal) {
                        showScannerDialog.value = true
                        isWinner = true
//                        scanAction = "Register As The Winner"
                    }
                }

                item {
                    AuctionItem(title = "SMS Reminders", R.drawable.ic_sms) {
//                        navController.navigate(Screen.SmsHandler.route)
                    }
                }
            }
        }

        ScannerDialog(showDialog = showScannerDialog) { code ->
            showScannerDialog.value = false
            CoroutineScope(Dispatchers.IO).launch {
                adminHomeViewModel.event(AdminEvents.OnScanUser(code))
                delay(100)
                if (!isWinner) {
                    showUserDialog.value = true
                } else {
                    showWinnerDialog.value = true
                }
            }
        }

        AdmitToAuction(scanAction, showUserDialog, adminHomeViewModel)
        RegisterWinnerDialog(showWinnerDialog, adminHomeViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuctionItem(title: String, icon: Int, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, bottom = 8.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(70.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = title,
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

    }
}