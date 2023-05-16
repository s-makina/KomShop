package com.komshop.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.komshop.R
import com.komshop.ui.theme.onBlue
import com.komshop.ui.viewmodel.AdminHomeViewModel
import com.komshop.util.Status

@Composable
fun AdmitToAuction(
    actionTxt: String,
    showDialog: MutableState<Boolean>,
    adminHomeViewModel: AdminHomeViewModel
) {
    val fetchUserEvent = adminHomeViewModel.fetchingUserEvent.collectAsState(initial = null).value

    if (showDialog.value) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
//                Surface(onClick = { /*TODO*/ }, color = MaterialTheme.colorScheme.primary) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "User Details", modifier = Modifier
//                                .weight(1f)
//                                .padding(start = 8.dp)
//                        )
//                        IconButton(onClick = { showDialog.value = false }) {
//                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
//                        }
//                    }
//                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "", modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    IconButton(onClick = { showDialog.value = false }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.badge_verified),
                            contentDescription = null
                        )
                    }
                }


                when (fetchUserEvent?.status) {
                    Status.LOADING -> {
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                        }
                    }

                    Status.ERROR -> {
                        ErrorComponent("Error "+fetchUserEvent.message ?: "Unknown error")
                    }

                    Status.SUCCESS -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Salvation Makina",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp, top = 4.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "+265 881960016",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "Balance: K 5000,000",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                textAlign = TextAlign.Center,
                                color = onBlue
                            )

//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp)
//                    ) {
//                        UserRow("Name", "Salvation Makina")
//                        Divider()
//                        UserRow("Phone", "0881960016")
//                        Divider()
//                        UserRow("Balance", "k 5,000,000")
//                    }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(onClick = { /*TODO*/ }) {
                                    Text(text = actionTxt)
                                }
                            }
                        }
                    }

                    else -> Unit
                }


            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterWinnerDialog(showDialog: MutableState<Boolean>, adminHomeViewModel: AdminHomeViewModel) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
//                Surface(onClick = { /*TODO*/ }, color = MaterialTheme.colorScheme.primary) {
//                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//                        Text(text = "User Details", modifier = Modifier
//                            .weight(1f)
//                            .padding(start = 8.dp))
//                        IconButton(onClick = { showDialog.value = false }) {
//                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
//                        }
//                    }
//                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "", modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    IconButton(onClick = { showDialog.value = false }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.badge_trophy),
                            contentDescription = null
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Salvation Makina",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, top = 4.dp),
                        textAlign = TextAlign.Center
                    )

                    var amount by remember { mutableStateOf("") }
                    Text(
                        text = "Enter the winner's bid amount",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = { Text(text = "Amount") }
                    )

//                Column(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)) {
//                    UserRow("Name", "Salvation Makina")
//                    Divider()
//                    UserRow("Phone", "0881960016")
//                    Divider()
//                    UserRow("Balance", "k 5,000,000")
//                }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Register As Winner")
                        }
                    }

                }
            }
        }
    }
}


@Composable
fun ErrorComponent(text: String) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.img_no_feed), contentDescription = null)
        Text(text = text)
    }
}