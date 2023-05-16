package com.komshop.ui.events

sealed class CheckOutEvent {
    object Onload: CheckOutEvent()
}