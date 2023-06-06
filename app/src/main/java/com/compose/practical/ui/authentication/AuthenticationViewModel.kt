package com.compose.practical.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel : ViewModel() {

    val uiState = MutableStateFlow(AuthenticationState())

    private fun toggleAuthenticationMode() {
        val authenticationMode = uiState.value.authenticationMode
        val newAuthenticationMode = if (
            authenticationMode == AuthenticationMode.SIGN_IN
        ) {
            AuthenticationMode.SIGN_UP
        } else {
            AuthenticationMode.SIGN_IN
        }
        uiState.value = uiState.value.copy(
            authenticationMode = newAuthenticationMode
        )
    }

    private fun updateEmail(email: String) {
        uiState.value = uiState.value.copy(
            email = email
        )
    }

    private fun updatePassword(password: String) {
        val requirements = mutableListOf<PasswordRequirements>()
        if (password.length > 7) {
            requirements.add(PasswordRequirements.EIGHT_CHARACTERS)
        }
        if (password.any { it.isUpperCase() }) {
            requirements.add(PasswordRequirements.CAPITAL_LETTER)
        }
        if (password.any { it.isDigit() }) {
            requirements.add(PasswordRequirements.NUMBER)
        }
        uiState.value = uiState.value.copy(
            password = password,
            passwordRequirements = requirements.toList()
        )
    }

    private fun authenticate() {
        uiState.value = uiState.value.copy(
            isLoading = true
        )
        // trigger network request

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000L)

            withContext(Dispatchers.Main){
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = "Something Went Wrong"
                )
            }
        }

    }

    private fun dismissError() {
        uiState.value = uiState.value.copy(
            error = null
        )
    }

    fun handleEvent(authenticationEvent: AuthenticationEvent) {
        when (authenticationEvent) {
           is AuthenticationEvent.ToggleAuthenticationMode -> {
               toggleAuthenticationMode()
           }

            is AuthenticationEvent.EmailChanged -> {
                updateEmail(authenticationEvent.emailAddress)
            }
            is AuthenticationEvent.PasswordChanged -> {
                updatePassword(authenticationEvent.password)
            }

            is AuthenticationEvent.Authenticate -> {
              authenticate()
            }

            is AuthenticationEvent.ErrorDismissed -> {
                dismissError()
            }
        }
    }

}