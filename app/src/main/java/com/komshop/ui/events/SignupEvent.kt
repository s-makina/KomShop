package com.komshop.ui.events

sealed class SignupEvent {
    class OnNameChange(val name: String) : SignupEvent()
    class OnFirstNameChange(val firstname: String) : SignupEvent()
    class OnLatNameChange(val lastname: String) : SignupEvent()
    class OnPhoneChange(val phone: String) : SignupEvent()
    class OnEmailChange(val email: String) : SignupEvent()
    class OnPasswordChange(val password: String) : SignupEvent()
    class OnConfirmPassChange(val confirmPass: String) : SignupEvent()
    class OnAcceptTerms(val acceptTerms: Boolean) : SignupEvent()
    object OnLogin : SignupEvent()
    object OnSubmit : SignupEvent()
}