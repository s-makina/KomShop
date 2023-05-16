package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.repo.AdminHomeRepo
import com.komshop.ui.events.AdminEvents
import com.komshop.ui.pages.bid.presentation.AdminHomeUiState
import com.komshop.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminHomeViewModel @Inject constructor(
    private val adminHomeRepo: AdminHomeRepo
) : ViewModel() {

    var state by mutableStateOf(AdminHomeUiState())

    private val fetchingUserEventChannel = Channel<Resource<Any>>()
    val fetchingUserEvent = fetchingUserEventChannel.consumeAsFlow()

    fun event(event: AdminEvents) {
        when(event) {
            is AdminEvents.OnScanUser -> {
                state = state.copy(code = event.code)
                fetchUser()
            }
            is AdminEvents.OnSubmitBidAmount -> {}
            is AdminEvents.OnAdmitToAuction -> {}
        }
    }

    private fun fetchUser() {
        val code = state.code ?: return
        viewModelScope.launch(Dispatchers.IO) {
            adminHomeRepo.getUser(code).collectLatest {
                fetchingUserEventChannel.send(it)
            }
        }
    }


    private fun admitToAuction() {
        val code = state.code ?: return
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}