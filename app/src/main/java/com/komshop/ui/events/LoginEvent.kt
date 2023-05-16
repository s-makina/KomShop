package net.daylightmusic.ui.events

sealed class LoginEvent {
    class OnEmailChange(val email: String): LoginEvent()
    class OnPasswordChange(val password: String): LoginEvent()
    class OnCompanyChange(val company: String): LoginEvent()
    class OnNameChange(val name: String): LoginEvent()
    object OnSubmit: LoginEvent()
    object OnSignup: LoginEvent()
}