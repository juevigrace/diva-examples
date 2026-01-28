package com.diva.social.data.interaction

import com.diva.database.social.interaction.InteractionStorage

class InteractionServiceImpl(
    private val storage: InteractionStorage,
) : InteractionService
