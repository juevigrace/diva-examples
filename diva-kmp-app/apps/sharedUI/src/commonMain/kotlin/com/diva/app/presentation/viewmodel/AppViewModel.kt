package com.diva.app.presentation.viewmodel

import com.diva.app.presentation.state.AppState
import io.github.juevigrace.diva.ui.viewmodel.DivaViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : DivaViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()


}
