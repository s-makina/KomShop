package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.Session
import com.komshop.data.repo.UserRepo
import com.komshop.ui.events.SignupEvent
import com.komshop.ui.pages.bid.presentation.LoginState
import com.komshop.ui.usecases.Validators
import com.komshop.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {
    var state by mutableStateOf(LoginState())
    private val validators = Validators()

    private val loginSignupEventChannel = Channel<Resource<Any>>()
    val loginSignupEvent = loginSignupEventChannel.receiveAsFlow()

    fun event(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnFirstNameChange -> {
                state = state.copy(firstname = event.firstname, nameError = null)
            }

            is SignupEvent.OnLatNameChange -> {
                state = state.copy(lastname = event.lastname, nameError = null)
            }

            is SignupEvent.OnNameChange -> {
                state = state.copy(name = event.name, nameError = null)
            }

            is SignupEvent.OnEmailChange -> {
                state = state.copy(email = event.email, emailError = null)
            }

            is SignupEvent.OnPhoneChange -> {
                state = state.copy(phone = event.phone, phoneError = null)
            }

            is SignupEvent.OnPasswordChange -> {
                state = state.copy(password = event.password, passwordError = null)
            }

            is SignupEvent.OnAcceptTerms -> {
                val error: String? = if(!event.acceptTerms) null else ""
                state = state.copy(acceptTerms = event.acceptTerms, acceptTermsError = error )
            }

            is SignupEvent.OnConfirmPassChange -> {
                var error: String? = null
                if (state.password != event.confirmPass) {
                    error = "Does not match"
                }
                state = state.copy(confirmPass = event.confirmPass, confirmPassError = error)
            }

            is SignupEvent.OnSubmit -> {
                signup()
            }

            is SignupEvent.OnLogin-> {
                login()
            }
        }
    }

    private fun signup() {
        val firstnameResult = validators.validateName.execute(state.firstname)
        val lastNameResult = validators.validateName.execute(state.lastname)
        val phoneResult = validators.validateRequired.execute(state.phone)
        val passwordResult = validators.validateRequired.execute(state.password)

        val hasErrors = listOf(
            firstnameResult,
            lastNameResult,
            phoneResult,
            passwordResult,
        ).any { !it.successful }

        if (hasErrors) {
            state = state.copy(
                firstnameError = firstnameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage,
                phoneError = phoneResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
//            loginSignupEventChannel.send(Resource.success(""))
            userRepo.signup(
                name = state.firstname+" "+state.lastname,
                phone = state.phone,
                email = state.email,
                password = state.password
            ).collectLatest {
                loginSignupEventChannel.send(it)
            }
        }
    }

    private fun login() {
        val phoneResult = validators.validateRequired.execute(state.phone)
        val passwordResult = validators.validateRequired.execute(state.password)

        val hasErrors = listOf(
            phoneResult,
            passwordResult,
        ).any { !it.successful }
        if (hasErrors) {
            state = state.copy(
                phoneError = phoneResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
//            loginSignupEventChannel.send(Resource.success(""))
            userRepo.authenticate(phone = state.phone, password = state.password).collectLatest {
                loginSignupEventChannel.send(it)
            }
        }
    }

//    private val _signupUser: MutableState<Resource<User>> = mutableStateOf(Resource.idle<String>())
//    val signupUser: State<Resource<User>> = _signupUser

//    fun signup(
//        name: String,
//        phone: String,
//        email: String,
//        password: String,
//        repeatPass: String
//    ) {
//        if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
//            _signupUser.value = Resource.error("Name, Phone and Password are required fields", null)
//            return
//        }
//
//        if (repeatPass != password) {
//            _signupUser.value = Resource.error("Passwords does not match", null)
//            return
//        }
//
//        viewModelScope.launch(Dispatchers.IO) {
//            userRepo.signup(name, phone, email, password).collectLatest {
//                _signupUser.value = it
//            }
//        }
//    }

    fun isUserAuthenticated() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getAuthenticatedUser().collectLatest { user ->
                if (user != null) {
                    Session.authUser.value = user
                }
                state = state.copy(user = user)
            }
        }
    }
}