package com.diva.auth.presentation.signIn.state

import com.diva.models.auth.SignUpForm

data class SignUpState(
    val signUpForm: SignUpForm = SignUpForm(),
//    val formValidation: SignUpValidation = SignUpValidation(),

    val submitLoading: Boolean = false,
    val submitError: String? = null,
//    val submitEnabled: Boolean = formValidation.valid(),
    val submitSuccess: Boolean = false,
)
