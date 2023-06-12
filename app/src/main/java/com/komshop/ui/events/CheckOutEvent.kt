package com.komshop.ui.events

sealed class CheckOutEvent {
    object Onload: CheckOutEvent()
    class OnFirstNameChange(val firstName: String): CheckOutEvent()
    class OnLastNameChange(val lastName: String): CheckOutEvent()
    class OnPhoneChange(val phone: String): CheckOutEvent()
    object OnSubmit: CheckOutEvent()
}