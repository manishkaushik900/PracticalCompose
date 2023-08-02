package com.compose.practical.ui.emailInbox

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class InboxViewModel:ViewModel() {

    val uiState = MutableStateFlow(InboxState())

    fun loadContent(){
        uiState.value = uiState.value.copy(
            status = InboxStatus.LOADING
        )

        uiState.value = uiState.value.copy(
            status = InboxStatus.SUCCESS,
            content  = EmailFactory.makeEmailList()
        )
    }
}