package com.diva.social.data.interaction

import com.diva.database.social.interaction.InteractionStorage
import com.diva.social.api.client.interaction.InteractionClient

class InteractionRepositoryImpl(
    private val client: InteractionClient,
    private val storage: InteractionStorage,
) : InteractionRepository
