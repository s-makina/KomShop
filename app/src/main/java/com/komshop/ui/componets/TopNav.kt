package com.komshop.ui.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.R
import com.komshop.navigation.Screen
import com.komshop.share
import com.komshop.ui.events.NotificationEvents
import com.komshop.ui.pages.UnderLinedEditText
import com.komshop.ui.viewmodel.NotificationViewModel
import java.net.URI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNav(
    navController: NavHostController,
    title: String = "",
    subtitle: String = "",
    searchIcon: Boolean = false,
    backButton: Boolean = false,
) {
    val notificationViewModel: NotificationViewModel = hiltViewModel()
    val state = notificationViewModel.state

    var showSearch by remember { mutableStateOf(false) }
    val icon = if (showSearch) Icons.Default.Close else Icons.Default.Search
    val expanded = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true, block = {
        notificationViewModel.event(NotificationEvents.OnLoadData)
    })

    Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primary) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                onClick = { navController.popBackStack() },
                modifier = Modifier,
                color = Color.Transparent
            ) {
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    if (backButton) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    } else {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Image(
                        painter = painterResource(id = R.drawable.kom_shop_logo),
                        contentDescription = null,
                        modifier = Modifier.height(30.dp)
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
                    modifier = Modifier
                        .defaultMinSize(20.dp)
                        .padding(end = 8.dp),
                    badge = {
                        Badge(
                            modifier = Modifier.offset(
                                x = (-15).dp,
                                y = (18).dp
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
}

@Composable
fun MenuDropDown(expanded: MutableState<Boolean>, onClick: (String) -> Unit = {}) {
    val context = LocalContext.current

    DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
        DropdownMenuItem(
            text = { Text("Share") },
            onClick = {
                expanded.value = false
                share(context, "https://play.google.com/store/apps/details?id=com.komshoponline")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = ""
                )
            })

        val uriHandler = LocalUriHandler.current

        DropdownMenuItem(
            text = { Text("Manage Products") },
            onClick = {
                expanded.value = false
                uriHandler.openUri("https://komshop.net/wp-admin")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ProductionQuantityLimits,
                    contentDescription = ""
                )
            })

        DropdownMenuItem(
            text = { Text("About KomShop") },
            onClick = {
                expanded.value = false
                onClick(Screen.AboutUs.route)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ProductionQuantityLimits,
                    contentDescription = ""
                )
            })
    }
}