package com.diva.auth.presentation.signUp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.diva.auth.presentation.signUp.events.SignUpEvents
import com.diva.auth.presentation.signUp.state.SignUpState
import com.diva.auth.presentation.signUp.viewmodel.SignUpViewModel
import com.diva.core.resources.Res
import com.diva.core.resources.alias
import com.diva.core.resources.already_have_account
import com.diva.core.resources.birth_date
import com.diva.core.resources.confirm_password
import com.diva.core.resources.email
import com.diva.core.resources.hide_password
import com.diva.core.resources.ic_eye
import com.diva.core.resources.ic_eye_off
import com.diva.core.resources.ic_lock
import com.diva.core.resources.ic_user
import com.diva.core.resources.password
import com.diva.core.resources.privacy_policy
import com.diva.core.resources.show_password
import com.diva.core.resources.sign_in
import com.diva.core.resources.sign_up
import com.diva.core.resources.terms_and_conditions
import com.diva.core.resources.username
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.ui.components.layout.Screen
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinViewModel()
) {
    val state: SignUpState by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    Screen { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.sign_up),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = state.signUpForm.email,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnEmailChanged(it)) },
                label = { Text(stringResource(Res.string.email)) },
                isError = state.formValidation.emailError is Option.Some,
                supportingText = when (val error = state.formValidation.emailError) {
                    is Option.Some -> { { Text(stringResource(error.value)) } }
                    is Option.None -> null
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.ic_user),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.signUpForm.username,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnUsernameChanged(it)) },
                label = { Text(stringResource(Res.string.username)) },
                isError = state.formValidation.usernameError is Option.Some,
                supportingText = when (val error = state.formValidation.usernameError) {
                    is Option.Some -> { { Text(stringResource(error.value)) } }
                    is Option.None -> null
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.ic_user),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.signUpForm.alias,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnAliasNameChanged(it)) },
                label = { Text(stringResource(Res.string.alias)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.ic_user),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            val formattedBirthDate = remember(state.signUpForm.birthDate) {
                if (state.signUpForm.birthDate > 0) {
                    val instant = Instant.fromEpochMilliseconds(state.signUpForm.birthDate)
                    val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault())
                    "${localDate.day.toString().padStart(
                        2,
                        '0'
                    )}/${localDate.month.number.toString().padStart(2, '0')}/${localDate.year}"
                } else {
                    ""
                }
            }

            OutlinedTextField(
                value = formattedBirthDate,
                onValueChange = {},
                label = { Text(stringResource(Res.string.birth_date)) },
                isError = state.formValidation.birthDateError is Option.Some,
                supportingText = when (val error = state.formValidation.birthDateError) {
                    is Option.Some -> { { Text(stringResource(error.value)) } }
                    is Option.None -> null
                },
                readOnly = true,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true }
            )

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = if (state.signUpForm.birthDate > 0) state.signUpForm.birthDate else null
                )
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                datePickerState.selectedDateMillis?.let {
                                    viewModel.onEvent(SignUpEvents.OnBirthDateChanged(it))
                                }
                                showDatePicker = false
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.signUpForm.password,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnPasswordChanged(it)) },
                label = { Text(stringResource(Res.string.password)) },
                isError = state.formValidation.passwordError is Option.Some,
                supportingText = when (val error = state.formValidation.passwordError) {
                    is Option.Some -> { { Text(stringResource(error.value)) } }
                    is Option.None -> null
                },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.ic_lock),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(
                                if (passwordVisible) Res.drawable.ic_eye_off else Res.drawable.ic_eye
                            ),
                            contentDescription = if (passwordVisible) {
                                stringResource(
                                    Res.string.hide_password
                                )
                            } else {
                                stringResource(Res.string.show_password)
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.signUpForm.confirmPassword,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnConfirmPasswordChanged(it)) },
                label = { Text(stringResource(Res.string.confirm_password)) },
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        if (state.submitEnabled) {
                            viewModel.onEvent(SignUpEvents.OnSubmit)
                        }
                    }
                ),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.ic_lock),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(
                                if (confirmPasswordVisible) Res.drawable.ic_eye_off else Res.drawable.ic_eye
                            ),
                            contentDescription = if (confirmPasswordVisible) {
                                stringResource(
                                    Res.string.hide_password
                                )
                            } else {
                                stringResource(Res.string.show_password)
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.signUpForm.termsAndConditions,
                    onCheckedChange = { viewModel.onEvent(SignUpEvents.ToggleTerms) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { viewModel.onEvent(SignUpEvents.ToggleTerms) }) {
                    Text(
                        text = stringResource(Res.string.terms_and_conditions),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.signUpForm.privacyPolicy,
                    onCheckedChange = { viewModel.onEvent(SignUpEvents.TogglePrivacyPolicy) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { viewModel.onEvent(SignUpEvents.TogglePrivacyPolicy) }) {
                    Text(
                        text = stringResource(Res.string.privacy_policy),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onEvent(SignUpEvents.OnSubmit) },
                enabled = state.submitEnabled && !state.submitLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (state.submitLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(stringResource(Res.string.sign_up))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.already_have_account),
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(
                    onClick = { viewModel.onEvent(SignUpEvents.OnNavigateToSignIn) }
                ) {
                    Text(stringResource(Res.string.sign_in))
                }
            }
        }
    }
}
