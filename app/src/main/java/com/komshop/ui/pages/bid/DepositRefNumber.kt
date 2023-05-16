package com.komshop.ui.pages.bid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.Config.process
import com.komshop.R
import com.komshop.enam.AuctionTypes
import com.komshop.navigation.Screen
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.SelectInput
import com.komshop.ui.dialog.Alert
import com.komshop.ui.dialog.AlertType
import com.komshop.ui.dialog.DepositSlipAdded
import com.komshop.ui.events.DepositEvent
import com.komshop.ui.viewmodel.DepositSlipViewModel
import com.komshop.util.Resource
import com.komshop.util.Status
import com.komshop.ui.componets.State

@Composable
fun DepositRefNumber(navController: NavHostController) {
    val depositSlipViewModel: DepositSlipViewModel = hiltViewModel()
    val state = depositSlipViewModel.state
    val validationState = depositSlipViewModel.validationEvent.collectAsState(initial = null).value
    val loadingState = depositSlipViewModel.loadingEvent.collectAsState(initial = null).value
    val successDialog = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        depositSlipViewModel.event(DepositEvent.OnLoadDeposits)
    }

    PageBackground(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Row {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bank Deposit slip",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Enter your deposit slip Reference number",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }

                IconButton(onClick = { depositSlipViewModel.event(DepositEvent.OnLoadDeposits) }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                }
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 0.dp, bottomStart = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SelectInput(
                        modifier = Modifier,
                        selectedOptionText = state.selectedAuctionType?.name ?: "",
                        options = state.auctionTypes.map { it.name },
                        placeHolder = "Select Auction Type",
                        onSelected = {
                            depositSlipViewModel.event(DepositEvent.OnSelectAuctionType(auctionType = it))
                        },
                        isError = state.selectedAuctionTypeError != null
                    )
                    OutlinedTextField(
                        value = state.currentDepositSlipNumber,
                        onValueChange = { depositSlipViewModel.event(DepositEvent.OnChangeDeposit(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        label = { Text(text = "Bank Deposit Slip Number") },
                        isError = state.currentDepositSlipNumberError != null
                    )
                    Row(modifier = Modifier.padding(top = 8.dp)) {
                        Button(
                            onClick = { depositSlipViewModel.event(DepositEvent.OnSubmit) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Submit")
                        }

                    }
                    TextButton(
                        onClick = {
                            navController.navigate(
                                Screen.SelectAuction.getRoute(
                                    AuctionTypes.LIVE_AUCTION,
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "SKIP THIS STEP")
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            if (state.otherDeposits.isNotEmpty()) {
                Spacer(modifier = Modifier.weight(1f))

                state.otherDeposits.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text(text = it, modifier = Modifier.weight(1f))
//                            IconButton(onClick = {
//                                depositSlipViewModel.event(
//                                    DepositEvent.OnDeleteDeposit(
//                                        it
//                                    )
//                                )
//                            }) {
//                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
//                            }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            if (validationState?.status == Status.LOADING) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                DepositInfo()
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        DepositSlipAdded(successDialog, onClose = {}, addAnother = {
            depositSlipViewModel.event(DepositEvent.OnAddAnother)
            successDialog.value = false
        })
    }

    when (validationState?.status) {
        Status.LOADING -> {}
        Status.ERROR -> {}
        Status.SUCCESS -> {
            successDialog.value = true
            depositSlipViewModel.event(DepositEvent.Reset)
        }
        else -> Unit
    }

    LoadingDialog(validationState)
    LoadingDialog(loadingState)
}

@Composable
fun LoadingDialog(res: Resource<Any>?, successMessage: String = "") {
    when (res?.status) {
        Status.LOADING -> {
            process.value = State.LOADING
        }

        Status.SUCCESS -> {
            process.value = State.IDLE
            if (successMessage.isNotEmpty()) {
                Alert(content = successMessage, alertType = AlertType.SUCCESS)
            }
        }

        Status.ERROR -> {
            process.value = State.IDLE
            Alert(content = res.message ?: "", alertType = AlertType.ERROR)
        }

        else -> Unit
    }
}

@Composable
fun DepositInfo() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.img_number_verification),
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )
    }
}