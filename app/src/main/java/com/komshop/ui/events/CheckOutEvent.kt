package com.komshop.ui.events

import android.content.Context

sealed class CheckOutEvent {
    object Onload: CheckOutEvent()
    class OnFirstNameChange(val firstName: String): CheckOutEvent()
    class OnLastNameChange(val lastName: String): CheckOutEvent()
    class OnCityChange(val city: String): CheckOutEvent()
    class OnPhoneChange(val phone: String): CheckOutEvent()
    class OnSubmitWhatsapp(val context: Context): CheckOutEvent()
    object OnSubmit: CheckOutEvent()
}