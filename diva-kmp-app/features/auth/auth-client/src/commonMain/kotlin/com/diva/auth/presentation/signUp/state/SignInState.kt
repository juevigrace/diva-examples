package com.diva.auth.presentation.signUp.state

import com.diva.models.auth.SignInForm

data class SignInState(
    val signInForm: SignInForm = SignInForm(),
//    val formValidation: SignInValidation = SignInValidation(),

    val submitLoading: Boolean = false,
    val submitEnabled: Boolean = false,
)
