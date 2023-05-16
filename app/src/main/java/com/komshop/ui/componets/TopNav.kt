package com.komshop.ui.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.R
import com.komshop.navigation.Screen
import com.komshop.ui.events.NotificationEvents
import com.komshop.ui.pages.UnderLinedEditText
import com.komshop.ui.viewmodel.NotificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNav(
    navController: NavHostController,
    title: String = "",
    subtitle: String = "",
    searchIcon: Boolean = false,
) {
    val notificationViewModel: NotificationViewModel = hiltViewModel()
    val state = notificationViewModel.state

    var showSearch by remember { mutableStateOf(false) }
    val icon = if (showSearch) Icons.Default.Close else Icons.Default.Search
    val expanded = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true, block = {
        notificationViewModel.event(NotificationEvents.OnLoadData)
    })

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.size(60.dp)) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                Image(
                    painter = painterResource(id = R.drawable.trust_auctioneer_logo),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showSearch) {
                UnderLinedEditText(
                    value = "",
                    placeholderText = "Search",
                    onValueChange = {},
                    textAlign = TextAlign.Left,
                    contentAlignment = Alignment.CenterStart
                )
            } else {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
//                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (subtitle.isNotEmpty())
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    )
            }
        }

        if (searchIcon) {
            IconButton(onClick = { showSearch = showSearch.not() }) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }

        if (state.notificationItemCount > 0) {
            BadgedBox(
                modifier = Modifier.defaultMinSize(20.dp),
                badge = {
                    Badge(
                        modifier = Modifier.offset(
                            x = (-12).dp,
                            y = (10).dp
                        )
                    ) { Text(text = state.notificationItemCount.toString()) }
                }) {
                IconButton(onClick = { navController.navigate(Screen.CartPage.route) }) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                }
            }
        }

        if (!showSearch) {
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Button"
                )
                MenuDropDown(expanded = expanded, onClick = {
                    if (it == "logout") {
//                    settingsViewModel.logout(context)

//                            navController.navigate(Screen.LoginPage.route) {
//                                popUpTo(Screen.LoginPage.route)
//                            }
                    } else {
                        navController.navigate(it) {
                            launchSingleTop = true
                        }
                    }
                })
            }
        }

//        IconButton(onClick = { navController.navigate(Screen.AdminHome.route) }) {
//            Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null)
//        }
    }
}

@Composable
fun MenuDropDown(expanded: MutableState<Boolean>, onClick: (String) -> Unit = {}) {
    DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
        DropdownMenuItem(
            text = { Text("Admin Section") },
            onClick = {
                expanded.value = false
                onClick(Screen.AdminHome.route)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user_allow),
                    contentDescription = ""
                )
            })
//
        DropdownMenuItem(
            text = { Text("Live Auctions") },
            onClick = {
                expanded.value = false
                onClick(Screen.SelectAuction.route)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_house_auction),
                    contentDescription = ""
                )
            })

        DropdownMenuItem(
            text = { Text("Online Auctions") },
            onClick = {
                expanded.value = false
                onClick(Screen.OnlineAuction.route)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_internet),
                    contentDescription = ""
                )
            })

        DropdownMenuItem(
            text = { Text("My bids") },
            onClick = {
                expanded.value = false
                onClick(Screen.MyBids.route)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_auction),
                    contentDescription = ""
                )
            })

        DropdownMenuItem(
            text = { Text("Logout") },
            onClick = {
                expanded.value = false
                onClick("logout")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = ""
                )
            })
    }
}