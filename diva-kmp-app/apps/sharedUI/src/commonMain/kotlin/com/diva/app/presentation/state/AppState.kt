package com.diva.app.presentation.state

import com.diva.ui.navigation.Destination

data class AppState(
    val startDestination: Destination = Destination.SignIn,
)
