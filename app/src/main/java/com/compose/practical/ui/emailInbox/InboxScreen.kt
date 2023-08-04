package com.compose.practical.ui.emailInbox

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.practical.R
import com.compose.practical.ui.emailInbox.model.Email
import com.compose.practical.ui.emailInbox.state.InboxState
import com.compose.practical.ui.emailInbox.state.InboxStatus


@Composable
fun Inbox() {

    val viewModel: InboxViewModel = viewModel()


    MaterialTheme {
        EmailInbox(
            modifier = Modifier.fillMaxWidth(),
            inboxState = viewModel.uiState.collectAsState().value,
            inboxEventListener = viewModel::handleEvent
        )

    }

    LaunchedEffect(Unit) {
        viewModel.loadContent()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInbox(
    modifier: Modifier = Modifier,
    inboxState: InboxState,
    inboxEventListener: (event: InboxEvent) -> Unit

) {

    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.title_inbox, inboxState.content?.size!!)
                    )
                }
            )
        }
    ) {

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            if (inboxState.status == InboxStatus.LOADING) {
                Loading()
            } else if (inboxState.status == InboxStatus.ERROR) {
                ErrorState {
                    inboxEventListener(InboxEvent.RefreshContent)
                }
            } else {
                EmptyState {
                    inboxEventListener(InboxEvent.RefreshContent)
                }
            }


        }

    }

}

@Composable
fun EmailList(
    modifier: Modifier = Modifier,
    emails: List<Email>
) {
    LazyColumn(
        modifier = modifier
    ) {
    }
}

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    inboxEventListener: (inboxEvent: InboxEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(
                id =
                R.string.message_empty_content
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            inboxEventListener(InboxEvent.RefreshContent)
        }) {
            Text(
                text = stringResource(
                    id =
                    R.string.label_check_again
                )
            )
        }
    }
}

@Composable
fun ErrorState(
    modifier: Modifier = Modifier,
    inboxEventListener: (inboxEvent: InboxEvent) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(
                id = R.string.message_content_error
            )
        )

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            inboxEventListener(InboxEvent.RefreshContent)
        }) {
            Text(
                text = stringResource(
                    id = R.string.label_try_again
                )
            )
        }
    }
}

@Composable
fun Loading() {

    CircularProgressIndicator()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun EmailInboxPreview() {
    Inbox()

}
