package com.komshop.ui.pages.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.komshop.ui.componets.TopNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SMSHandler(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNav(
                title = "Sms Handler",
                navController = navController,
//                subtitle = "Find your registered auctions"
            )
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = { Text("") },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painterResource(id = com.komshop.R.drawable.ic_decal2),
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.BottomEnd
                ),
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
            )
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigureSms(showDialog: MutableState<Boolean> = mutableStateOf(false)) {
    if (showDialog.value) {
        AlertDialog(onDismissRequest = { /*TODO*/ }) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Send out sms notification")
                }
            }
        }
    }
}