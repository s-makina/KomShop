package com.komshop.ui.componets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.komshop.ChangeStatusBarColor
import com.komshop.enam.AuctionTypes
import com.komshop.ui.theme.orangeBg


@Composable
fun AuctionPageBackGround(
    modifier: Modifier = Modifier,
    auctionTypes: AuctionTypes,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    var bgColor = MaterialTheme.colorScheme.primary

    if (auctionTypes == AuctionTypes.ONLINE_AUCTION) {
        bgColor = orangeBg
    }

    ChangeStatusBarColor(bgColor)

    PageBackground(
        containerColor = bgColor,
        topBar = {
            topBar()
        },
        floatingActionButton = {
            floatingActionButton()
        },
        bottomBar = {bottomBar()}
    ) {
        content()
    }
}