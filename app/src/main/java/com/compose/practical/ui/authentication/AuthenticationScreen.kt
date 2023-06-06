package com.compose.practical.ui.authentication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.practical.R

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Authentication()
}

@Composable
fun Authentication() {
    val viewModel: AuthenticationViewModel = viewModel()

    MaterialTheme {

        AuthenticationContent(
            modifier = Modifier.fillMaxSize(),
            authenticationState = viewModel.uiState.collectAsState().value,
            handleEvent = viewModel::handleEvent
        )
    }
}


@Composable
fun AuthenticationContent(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    handleEvent: (event: AuthenticationEvent) -> Unit
) {

    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {

        if (authenticationState.isLoading) {
            CircularProgressIndicator()
        } else {

            AuthenticationForm(
                modifier = Modifier.fillMaxSize(),
                authenticationState.authenticationMode,
                email = authenticationState.email,
                onEmailChange = { email ->
                    handleEvent(
                        AuthenticationEvent.EmailChanged(email)
                    )

                },
                password = authenticationState.password,
                onPasswordChange = { password ->
                    handleEvent(AuthenticationEvent.PasswordChanged(password))
                },
                onAuthenticate = {
                    handleEvent(AuthenticationEvent.Authenticate)
                                 },
                passwordRequirements = authenticationState.passwordRequirements,
                enabledAuthentication = authenticationState.isFormValid(),
                onToggleMode = {
                    handleEvent(AuthenticationEvent.ToggleAuthenticationMode)
                }
            )

            authenticationState.error?.let { error ->
                AuthenticationErrorDialog(
                    error = error,
                    dismissError = {
                        handleEvent(
                            AuthenticationEvent.ErrorDismissed
                        )
                    }
                )
            }

        }

    }

}

@Composable
fun AuthenticationForm(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    email: String?,
    onEmailChange: (email: String) -> Unit,
    password: String?,
    onPasswordChange: (password: String) -> Unit,
    onAuthenticate: () -> Unit,
    passwordRequirements: List<PasswordRequirements>,
    enabledAuthentication: Boolean,
    onToggleMode: () -> Unit

) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        AuthenticationTitle(authenticationMode = authenticationMode)

        Spacer(modifier = Modifier.height(32.dp))
        val passwordFocusRequester = FocusRequester()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                EmailInput(
                    modifier = Modifier.fillMaxWidth(), email = email, onEmailChange = onEmailChange
                ) {
                    passwordFocusRequester.requestFocus()
                }

                Spacer(modifier = Modifier.height(16.dp))

                PasswordInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester),
                    password = password,
                    onPasswordChange = onPasswordChange,
                    onDoneClicked = onAuthenticate
                )
                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = authenticationMode == AuthenticationMode.SIGN_UP
                ) {
                    PasswordRequirements(
                        modifier = Modifier.fillMaxWidth(),
                        satisfiedRequirements = passwordRequirements
                    )
                }


                Spacer(modifier = Modifier.height(12.dp))

                AuthenticationButton(
                    authenticationMode = authenticationMode,
                    enabledAuthentication = enabledAuthentication,
                    onAuthenticate = onAuthenticate
                )


            }
        }

        Spacer(modifier = Modifier.weight(1f))

        ToggleAuthenticationMode(
            modifier = Modifier.fillMaxWidth(),
            authenticationMode = authenticationMode,
            toggleAuthentication = {
                onToggleMode()
            }
        )


    }

}

@Composable
fun AuthenticationErrorDialog(
    modifier: Modifier = Modifier,
    error: String,
    dismissError: () -> Unit
) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { dismissError() },
        confirmButton = {
            TextButton(
                onClick = {
                    dismissError()
                }
            ) {
                Text(text = stringResource(id = R.string.error_action))
            }
        },
        title = {
            Text(
                text = stringResource(
                    id = R.string.error_title
                ),
                fontSize = 18.sp
            )
        },
        text = {
            Text(
                text = error
            )
        }

    )

}

@Composable
fun ToggleAuthenticationMode(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    toggleAuthentication: () -> Unit
) {
    Surface(
        modifier = modifier
            .padding(top = 16.dp),
        shadowElevation = 8.dp,
    ) {
        TextButton(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp),
            onClick = {
                toggleAuthentication()
            }
        ) {
            Text(
                text = stringResource(
                    if (authenticationMode ==
                        AuthenticationMode.SIGN_IN
                    ) {
                        R.string.action_need_account
                    } else {
                        R.string.action_already_have_account
                    }
                )
            )
        }
    }
}


@Composable
fun AuthenticationButton(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    enabledAuthentication: Boolean,
    onAuthenticate: () -> Unit
) {
    Button(
        modifier = modifier, onClick = {
            onAuthenticate()
        },
        enabled = enabledAuthentication
    ) {
        Text(
            text = stringResource(
                if (authenticationMode == AuthenticationMode.SIGN_IN) {
                    R.string.action_sign_in
                } else {
                    R.string.action_sign_up
                }
            )
        )
    }
}

@Composable
fun PasswordRequirements(
    modifier: Modifier = Modifier, satisfiedRequirements: List<PasswordRequirements>
) {
    Column(modifier = modifier) {
        PasswordRequirements.values().forEach { requirement ->
            Requirement(
                message = stringResource(id = requirement.label),
                satisfied = satisfiedRequirements.contains(requirement)
            )
        }
    }
}

@Composable
fun Requirement(
    modifier: Modifier = Modifier, message: String, satisfied: Boolean
) {
    val tint = if (satisfied) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    }

    val requirementStatus = if (satisfied) {
        stringResource(
            id = R.string.password_requirement_satisfied, message
        )
    } else {
        stringResource(
            id = R.string.password_requirement_needed, message
        )
    }

    Row(
        modifier = modifier
            .padding(6.dp)
            .semantics(mergeDescendants = true) {
                text = AnnotatedString(
                    requirementStatus
                )
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.clearAndSetSemantics { },
            text = message,
            fontSize = 12.sp,
            color = tint
        )

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    password: String?,
    onPasswordChange: (password: String) -> Unit,
    onDoneClicked: () -> Unit
) {

    var isPasswordHidden by remember { mutableStateOf(true) }

    TextField(modifier = modifier, keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
    ), keyboardActions = KeyboardActions(onDone = {
        onDoneClicked()
    }), value = password ?: "", onValueChange = {
        onPasswordChange(it)
    }, label = { Text(text = stringResource(id = R.string.label_password)) }, leadingIcon = {
        Icon(
            imageVector = Icons.Default.Lock, contentDescription = null
        )
    }, trailingIcon = {

        Icon(
            modifier = Modifier.clickable(
                onClickLabel = if (isPasswordHidden) {
                    stringResource(id = R.string.cd_show_password)
                } else {
                    stringResource(id = R.string.cd_hide_password)
                }
            ) { isPasswordHidden = !isPasswordHidden }, imageVector = if (isPasswordHidden) {
                Icons.Default.Visibility
            } else {
                Icons.Default.VisibilityOff
            }, contentDescription = null
        )

    }, singleLine = true, visualTransformation = if (isPasswordHidden) {
        PasswordVisualTransformation()
    } else VisualTransformation.None
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String?,
    onEmailChange: (email: String) -> Unit,
    onNextClicked: () -> Unit
) {
    TextField(modifier = modifier, keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Email,
    ), keyboardActions = KeyboardActions(onNext = {
        onNextClicked()
    }), value = email ?: "", onValueChange = {
        onEmailChange(it)

    }, label = { Text(text = stringResource(id = R.string.label_email)) }, leadingIcon = {
        Icon(
            imageVector = Icons.Default.Email, contentDescription = null
        )
    }, singleLine = true
    )
}

@Composable
fun AuthenticationTitle(
    modifier: Modifier = Modifier, authenticationMode: AuthenticationMode
) {
    Text(
        text = stringResource(
            id = if (authenticationMode == AuthenticationMode.SIGN_IN) {
                R.string.label_sign_in_to_account
            } else {
                R.string.label_sign_up_for_account
            }
        ), fontSize = 24.sp, fontWeight = FontWeight.Black

    )


}