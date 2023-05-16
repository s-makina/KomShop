package com.komshop.ui.pages.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.komshop.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.data.Session.processing
import com.komshop.navigation.Screen
import com.komshop.ui.dialog.Alert
import com.komshop.ui.dialog.AlertType
import com.komshop.ui.events.SignupEvent
import com.komshop.ui.viewmodel.UserViewModel
import com.komshop.util.Status

@Composable
fun Login(navController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val state = userViewModel.state


//    val context = LocalContext.current
//    LaunchedEffect(key1 = true, block = {
//        userViewModel.isUserAuthenticated()
//    })
//
//    LaunchedEffect(key1 = userViewModel.state.user, block = {
//        delay(1200)
//        var page = Screen.Login.route
//        if (userViewModel.state.user != null) {
//            page = Screen.DepositRefNumber.route
//        }
//        navController.navigate(page) {
//            launchSingleTop = true
//        }
//    })

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {}, containerColor = MaterialTheme.colorScheme.primary) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_back_arrow),
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
//                )
//
//            }


            var isPasswordVisible by remember { mutableStateOf(false) }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.trust_auctioneer_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp),
//                                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                            )
                            
//                            Text(text = "TAEA", style = MaterialTheme.typography.displayLarge)

                        }
                    }


                    val modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                    val inputShape = RoundedCornerShape(8.dp)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        ),
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = "Login",
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp),
                            )
                            OutlinedTextField(
                                modifier = modifier,
                                value = state.phone,
                                label = { Text(text = "Email/Phone") },
                                shape = inputShape,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                onValueChange = { userViewModel.event(SignupEvent.OnPhoneChange(it)) },
                                isError = state.phoneError != null,
                                maxLines = 1,
                                singleLine = true,
//                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                            )

//                    OutlinedTextField(
//                        modifier = modifier,
//                        value = state.company,
//                        label = { Text(text = "Company code") },
//                        shape = inputShape,
//                        trailingIcon = {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_briefcase),
//                                contentDescription = null,
//                            )
//                        },
//                        onValueChange = { userViewModel.event(LoginEvent.OnCompanyChange(it)) },
//                        isError = state.companyError != null
//                    )

                            OutlinedTextField(
                                modifier = modifier,
                                value = state.password,
                                label = { Text(text = "Password") },
                                shape = inputShape,
                                trailingIcon = {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_eye),
//                                contentDescription = null,
//                            )

                                    IconButton(onClick = {
                                        isPasswordVisible = !isPasswordVisible
                                    }) {
                                        val icon =
                                            if (isPasswordVisible) R.drawable.ic_eye_slash else R.drawable.ic_eye
                                        Icon(
                                            painter = painterResource(id = icon),
                                            contentDescription = "Password Toggle"
                                        )
                                    }
                                },
                                onValueChange = {
                                    userViewModel.event(
                                        SignupEvent.OnPasswordChange(
                                            it
                                        )
                                    )
                                },
                                isError = state.passwordError != null,
                                visualTransformation = if (!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(checked = true, onCheckedChange = {})
                                Text(text = "Remember me")
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = "Forgot Password?")
                            }

                            Button(
                                onClick = {
                                    userViewModel.event(SignupEvent.OnLogin)
                                },
                                modifier = modifier.padding(top = 8.dp),
                                shape = inputShape
                            ) {
                                Text(text = "Sign in")
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Don't have an account?",
                                    modifier = Modifier.padding(end = 4.dp)
                                )

                                Text(
                                    modifier = Modifier.clickable { navController.navigate(Screen.Register.route) },
                                    text = "Signup",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }

        val loginState = userViewModel.loginSignupEvent.collectAsState(initial = null).value

        when (loginState?.status) {
            Status.LOADING -> {
                processing.value = true
            }
            Status.SUCCESS -> {
                processing.value = false
                navController.navigate(Screen.DepositRefNumber.route) {
                    launchSingleTop = true
                }
            }
            Status.ERROR -> {
                processing.value = false
                Alert(title = "Error", content = "${loginState.message}", alertType = AlertType.ERROR)
            }
            else -> Unit
        }
    }
}