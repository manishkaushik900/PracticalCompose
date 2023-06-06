package com.compose.practical.ui.authentication

sealed class AuthenticationEvent {
    object ToggleAuthenticationMode : AuthenticationEvent()

    class EmailChanged(val emailAddress: String) :
        AuthenticationEvent()

    class PasswordChanged(val password: String) :
        AuthenticationEvent()

    object Authenticate:AuthenticationEvent()

    object ErrorDismissed: AuthenticationEvent()

}
