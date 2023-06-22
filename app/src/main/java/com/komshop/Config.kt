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
    const val API_ADDRESS = "https://komshop.net/"
    const val CONSUMER_KEY = "ck_2a679c710dfe09994c8bb08ccde16e0d9f753f52"
    const val SECRET_KEY = "cs_1ef197afb3ebce39636a71a23e40eb948132cba0"
//    const val API_ADDRESS = "https://dev.trustmw.com/api/"
    const val WHATSAPP_NUMBER = "265880803830"
    const val EMAIL = "hello@Komshoponline.com"
    const val app_version = 2
    val process = mutableStateOf(State.IDLE)
    val selectedAuctionType = mutableStateOf(AuctionTypes.CASH_AUCTION)
    const val sampleImage = "https://dev.trustmw.com/storage/80/_HMH8541.jpg"
}

@Composable
fun ChangeStatusBarColor(bgColor: Color) {
    val defaultColor =  MaterialTheme .colorScheme.primary
    LaunchedEffect(key1 = bgColor) {
        Session.statusColor.value = bgColor
    }
    DisposableEffect(true) {
        onDispose {
            Session.statusColor.value = defaultColor
        }
    }
}