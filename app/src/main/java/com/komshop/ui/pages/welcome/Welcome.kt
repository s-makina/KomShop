package com.komshop.ui.pages.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.komshop.R
import com.komshop.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPagerApi::class,
)
@Composable
fun Welcome(navController: NavHostController) {
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()
    var icon by remember {
        mutableStateOf(Icons.Default.ChevronRight)
    }
    var done = false

    icon = if (pagerState.currentPage < pagerState.pageCount-1) {
        done = false
        Icons.Default.ChevronRight
    } else {
        Icons.Default.Done
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primary,
        floatingActionButton = {

            FloatingActionButton(onClick = {
                if (pagerState.currentPage < pagerState.pageCount-1) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    if (done) {
                        // Navigate to next screen
                        navController.navigate(Screen.Login.route)
                    }
                    done = true
                }
            }) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {

                HorizontalPager(count = 3, state = pagerState) { page ->
                    when(page) {
                        0 -> WelcomeStep(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_megaphone),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                                )
                            },
                            title = "Welcome",
                            body = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed" +
                                    " incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam" +
                                    "quis nostrud exercitation"
                        )

                        1-> WelcomeStep(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_money_profit),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                                )
                            },
                            title = "Bid & Earn",
                            body = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed" +
                                    " incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam" +
                                    "quis nostrud exercitation"
                        )

                        2 -> WelcomeStep(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_referal),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                                )
                            },
                            title = "Check your bid",
                            body = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed" +
                                    " incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam" +
                                    "quis nostrud exercitation"
                        )
                    }


                }


            }

            Row(
                modifier = Modifier
                    .height(100.dp)
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.onPrimary,
                    inactiveColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f),
                    indicatorHeight = 8.dp,
                    indicatorWidth = 16.dp,
                    indicatorShape = RoundedCornerShape(4.dp),
                    spacing = 8.dp
                )
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    Welcome(rememberNavController())
}