package com.diva.app.presentation.viewmodel

import com.diva.app.presentation.state.AppState
import com.diva.ui.navigation.Destination
import com.diva.ui.navigation.OnboardingDestination
import io.github.juevigrace.diva.ui.navigation.Navigator
import io.github.juevigrace.diva.ui.viewmodel.DivaViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val navigator: Navigator<Destination>
) : DivaViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    fun initialize() {
        scope.launch {
            navigator.navigate(OnboardingDestination)
        }
    }
}
