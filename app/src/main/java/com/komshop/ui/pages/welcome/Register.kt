package com.komshop.ui.pages.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.R
import com.komshop.data.Session
import com.komshop.navigation.Screen
import com.komshop.ui.dialog.Alert
import com.komshop.ui.dialog.AlertType
import com.komshop.ui.events.SignupEvent
import com.komshop.ui.viewmodel.UserViewModel
import com.komshop.util.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(navController: NavHostController) {

    val userViewModel: UserViewModel = hiltViewModel()
    val state = userViewModel.state
    val loginState = userViewModel.loginSignupEvent.collectAsState(initial = null).value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {},
        containerColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

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
                                text = "Register",
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp),
                            )

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    modifier = modifier.weight(1f),
                                    value = state.firstname,
                                    label = { Text(text = "Firstname") },
                                    shape = inputShape,
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    onValueChange = { userViewModel.event(SignupEvent.OnFirstNameChange(it)) },
                                    isError = state.firstnameError != null,
                                    maxLines = 1,
                                    singleLine = true
                                )

                                OutlinedTextField(
                                    modifier = modifier.weight(1f),
                                    value = state.lastname,
                                    label = { Text(text = "Lastname") },
                                    shape = inputShape,
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    onValueChange = { userViewModel.event(SignupEvent.OnLatNameChange(it)) },
                                    isError = state.lastNameError != null,
                                    maxLines = 1,
                                    singleLine = true
                                )
                            }

                            OutlinedTextField(
                                modifier = modifier,
                                value = state.phone,
                                label = { Text(text = "Phone") },
                                shape = inputShape,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Phone,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                onValueChange = { userViewModel.event(SignupEvent.OnPhoneChange(it)) },
                                isError = state.phoneError != null,
                                maxLines = 1,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                            )

                            OutlinedTextField(
                                modifier = modifier,
                                value = state.email,
                                label = { Text(text = "Email (Optional)") },
                                shape = inputShape,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                onValueChange = { userViewModel.event(SignupEvent.OnEmailChange(it)) },
                                isError = state.emailError != null,
                                maxLines = 1,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
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

                            Button(
                                onClick = { userViewModel.event(SignupEvent.OnSubmit) },
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
                                    text = "Already have an account?",
                                    modifier = Modifier.padding(end = 4.dp)
                                )

                                Text(
                                    modifier = Modifier.clickable { navController.navigate(Screen.Login.route) },
                                    text = "Login",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                        }
                    }

                }
            }
        }


        when (loginState?.status) {
            Status.LOADING -> {
                Session.processing.value = true
            }
            Status.SUCCESS -> {
                Session.processing.value = false
                navController.navigate(Screen.Login.route) {
                    launchSingleTop = true
                }
            }
            Status.ERROR -> {
                Session.processing.value = false
                Alert(
                    title = "Error",
                    content = "${loginState.message}",
                    alertType = AlertType.ERROR
                )
            }
            else -> Unit
        }
    }
}