package com.compose.practical.ui.emailInbox

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class InboxViewModel : ViewModel() {

    val uiState = MutableStateFlow(InboxState())

    fun loadContent() {
        uiState.value = uiState.value.copy(
            status = InboxStatus.LOADING
        )

        uiState.value = uiState.value.copy(
            status = InboxStatus.SUCCESS,
            content = EmailFactory.makeEmailList()
        )
    }

    private fun deleteEmail(id: String) {
        uiState.value = uiState.value.copy(
            content = uiState.value.content?.filter {
                it.id != id
            }
        )
    }

    fun handleEvent(inboxEvent: InboxEvent) {
        when (inboxEvent) {
            InboxEvent.RefreshContent -> {
                loadContent()
            }

            is InboxEvent.DeleteEmail -> {
                deleteEmail(inboxEvent.id)
            }
        }
    }
}

sealed class InboxEvent {
    object RefreshContent : InboxEvent()
    class DeleteEmail(val id: String) : InboxEvent()

}
