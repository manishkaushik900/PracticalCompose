package com.compose.practical.ui.emailInbox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
//this is manish
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
                        text = stringResource(id = R.string.title_inbox, inboxState.content?.size?:"0")
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
            } else if (!inboxState.content.isNullOrEmpty()) {
                EmailList(emails = inboxState.content)
            } else {
                EmptyState {
                    inboxEventListener(InboxEvent.RefreshContent)
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailList(
    modifier: Modifier = Modifier,
    emails: List<Email>
) {
    var isEmailItemDismissed by remember { mutableStateOf(false) }
    var show by remember { mutableStateOf(true) }
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToEnd) {
                show = false
                isEmailItemDismissed = true
                true
            } else false
        }, positionalThreshold = { 150.dp.toPx() }
    )

    LazyColumn(
        modifier = modifier
    ) {
        items(emails, key = { item -> item.id }) { email: Email ->

            AnimatedVisibility(
                show, exit = fadeOut(spring())
            ) {
                SwipeToDismiss(
                    state = dismissState, background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.LightGray
                                DismissValue.DismissedToEnd -> Color.Green
                                DismissValue.DismissedToStart -> Color.Red
                            }, label = "Delete"
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                        ) {
                            Icon(
                                modifier = Modifier.align(Alignment.CenterStart),
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(
                                    id =
                                    R.string.cd_delete_email
                                )
                            )

                        }
                    },
                    directions = setOf(
                        DismissDirection.StartToEnd
                    ),
                    dismissContent = {
                        EmailItem(
                            email = email
                        )
                    }
                )
            }

        }
    }
}


@Composable
fun EmailItem(
    modifier: Modifier = Modifier,
    email: Email
) {
    Card(
        modifier = modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = email.title,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = email.description,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
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
