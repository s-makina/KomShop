package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.repo.AuctionTypeRepo
import com.komshop.log
import com.komshop.ui.events.DepositEvent
import com.komshop.ui.pages.bid.presentation.DepositSlipState
import com.komshop.ui.usecases.Validators
import com.komshop.util.Resource
import com.komshop.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepositSlipViewModel @Inject constructor(
    private val auctionTypeRepo: AuctionTypeRepo,
    private val validators: Validators
) : ViewModel() {

    var state by mutableStateOf(DepositSlipState())
    private val loadingEventChannel = Channel<Resource<Any>>()
    val loadingEvent = loadingEventChannel.receiveAsFlow()

    private val validationEventChannel = Channel<Resource<Any>>()
    val validationEvent = validationEventChannel.receiveAsFlow()

    fun event(event: DepositEvent) {
        when (event) {
            is DepositEvent.OnLoadDeposits -> {
                getData()
            }

            is DepositEvent.OnChangeDeposit -> {
                state = state.copy(currentDepositSlipNumber = event.deposit)
            }

            is DepositEvent.OnSelectAuctionType -> {
                state = state.copy( selectedAuctionType = state.auctionTypes.find { event.auctionType == it.name} )
            }

            is DepositEvent.OnDeleteDeposit -> {
                val list = state.otherDeposits.filter { event.deposit != it }
                state = state.copy(otherDeposits = list)
            }

            is DepositEvent.OnSubmit -> {
                validate()
            }

            is DepositEvent.OnAddAnother -> {
                if (state.currentDepositSlipNumber.isNotEmpty()) {
                    state = state.copy(otherDeposits = (state.otherDeposits + state.currentDepositSlipNumber))
                    state = state.copy(currentDepositSlipNumber = "")
                }
            }

            is DepositEvent.Reset -> {
                viewModelScope.launch {
                    validationEventChannel.send(Resource.idle<String>())
                }
            }

            is DepositEvent.OnCompleted -> {

            }
        }
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            auctionTypeRepo.getAuctionTypes().collectLatest {
                if (it.status == Status.SUCCESS) {
                    state = state.copy(auctionTypes = it.data ?: emptyList())
                }
                loadingEventChannel.send(it)
            }
        }
    }

    private fun validate() {
        val result = validators.validateDepositSlip.execute(state.currentDepositSlipNumber)
        val resultAuction = validators.validateRequired.execute(state.selectedAuctionType?.name ?: "")

        log(state.selectedAuctionType?.name)

        val hasErrors = listOf(
            result,
            resultAuction
        ).any { !it.successful }

        state = state.copy(currentDepositSlipNumberError = result.errorMessage)
        state = state.copy(selectedAuctionTypeError = resultAuction.errorMessage)

        if (hasErrors) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            auctionTypeRepo.submitDepositSlip(state.selectedAuctionType!!.id, state.currentDepositSlipNumber).collectLatest {
                validationEventChannel.send(it)
            }
        }
    }
}