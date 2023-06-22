package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.Config
import com.komshop.data.getTotalPrice
import com.komshop.data.model.CartItem
import com.komshop.data.repo.CartRepo
import com.komshop.formatMoney
import com.komshop.log
import com.komshop.sendWapMsg
import com.komshop.ui.events.CheckOutEvent
import com.komshop.ui.pages.bid.presentation.CheckOutUiState
import com.komshop.ui.usecases.Validators
import com.komshop.util.Resource
import com.komshop.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val cartRepo: CartRepo,
    private val validators: Validators,
) : ViewModel() {
    var state by mutableStateOf(CheckOutUiState())
    private val loadingChannel = Channel<Resource<Any>>()
    val submittingOrder = loadingChannel.consumeAsFlow()

    fun event(event: CheckOutEvent) {
        when (event) {
            is CheckOutEvent.Onload -> {
                loadItems()
            }

            is CheckOutEvent.OnFirstNameChange -> {
                state = state.copy(firstName = event.firstName)
            }

            is CheckOutEvent.OnLastNameChange -> {
                state = state.copy(lastName = event.lastName)
            }

            is CheckOutEvent.OnPhoneChange -> {
                state = state.copy(phone = event.phone)
            }

            is CheckOutEvent.OnCityChange -> {
                state = state.copy(city = event.city)
            }

            is CheckOutEvent.OnSubmitWhatsapp -> {
                if (validate()) {
                    log("SEND WHATSAPP MESSAGE")
                    sendWapMsg(event.context, Config.WHATSAPP_NUMBER, getMsg())
                    viewModelScope.launch {
                        loadingChannel.send(Resource.success("Success"))
                    }
                }
            }

            is CheckOutEvent.OnSubmit -> {
                if (validate()) {
                    submitOrder()
                }
            }
        }
    }

    private fun validate(): Boolean {

        val firstNameResult = validators.validateRequired.execute(state.firstName)
        val lastNameResult = validators.validateRequired.execute(state.lastName)
        val cityResult = validators.validateRequired.execute(state.city)
        val phoneResult = validators.validateRequired.execute(state.phone)

        val hasErrors = listOf(
            firstNameResult,
            lastNameResult,
            cityResult,
            phoneResult
        ).any { !it.successful }
        if (hasErrors) {
            state = state.copy(
                firstNameError = firstNameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage,
                cityError = cityResult.errorMessage,
                phoneError = phoneResult.errorMessage
            )
            return false
        }
        return true
    }

    private fun submitOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.submitOrder(state.firstName, state.lastName, state.city, state.phone, state.items).collectLatest {
                if (it.status == Status.SUCCESS) {
                    reset()
                }
                loadingChannel.send(it)
            }
        }
    }

    private fun reset() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                firstNameError = null,
                lastNameError = null,
                cityError = null,
                phoneError = null,
                firstName = "",
                lastName = "",
                city = "",
                phone = ""
            )
            cartRepo.clearItems()
        }
    }

    private fun loadItems() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.getCartItems().collectLatest {
                state = state.copy(items = it, it.sumOf { it.getTotalPrice() })
            }
        }
    }

    private fun getMsg(): String {
        var s = contactDetail()
        s += state.items.joinToString("\n") { getIt(it) }
        s += "*Grand Total: MK ${formatMoney(state.items.sumOf { it.getTotalPrice() })}*"
        return s
    }

    private fun contactDetail(): String {
        return """
            *Name:* ${state.firstName} ${state.lastName},%0a  
            *City:* ${state.city}, %0a  
            *Phone:* ${state.phone}, %0a  
           _________________ %0a
        """.trimIndent()
    }

    private fun getIt(item: CartItem): String {
        return """
            *Product:* ${item.name}, %0a
            *Quantity:* ${item.quantity} , %0a
            *Price:* ${formatMoney(item.price.toDouble())}, %0a
            *TotalPrice:* ${formatMoney(item.getTotalPrice())} %0a
            *Link:* ${item.permalink} %0a
            -------------------- %0a
        """.trimIndent()
    }
}