package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.repo.NotificationRepo
import com.komshop.ui.events.NotificationEvents
import com.komshop.ui.pages.bid.presentation.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepo: NotificationRepo
) : ViewModel() {
    var state by mutableStateOf(NotificationUiState())

    fun event(event: NotificationEvents) {
        when (event) {
            is NotificationEvents.OnLoadData -> {
                getCartNotificationCount()
            }
        }
    }

    private fun getCartNotificationCount() {
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepo.getTotalCartItems().collectLatest {
                state = state.copy(notificationItemCount = it)
            }
        }
    }
}