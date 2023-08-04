package com.compose.practical.ui.emailInbox.state

import com.compose.practical.ui.emailInbox.model.Email

enum class InboxStatus {
    LOADING, HAS_EMAILS, ERROR, EMPTY, SUCCESS
}

data class InboxState(
    val status: InboxStatus = InboxStatus.LOADING,
    val content: List<Email>? = null

)
