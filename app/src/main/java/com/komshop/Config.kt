package com.komshop

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.komshop.data.Session
import com.komshop.enam.AuctionTypes
import com.komshop.ui.componets.State

object Config {
    const val API_ADDRESS = "https://dev.trustmw.com/api/"
    const val app_version = 1
    val process = mutableStateOf(State.IDLE)
    val selectedAuctionType = mutableStateOf(AuctionTypes.CASH_AUCTION)
    const val sampleImage = "https://dev.trustmw.com/storage/80/_HMH8541.jpg"
}

@Composable
fun ChangeStatusBarColor(bgColor: Color) {
    val defaultColor =  MaterialTheme.colorScheme.primary
    LaunchedEffect(key1 = bgColor) {
        Session.statusColor.value = bgColor
    }
    DisposableEffect(true) {
        onDispose {
            Session.statusColor.value = defaultColor
        }
    }
}