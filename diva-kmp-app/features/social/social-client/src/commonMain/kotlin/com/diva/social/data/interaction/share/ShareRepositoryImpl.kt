package com.diva.social.data.interaction.share

import com.diva.database.social.share.ShareStorage
import com.diva.social.api.client.interaction.share.ShareClient

class ShareRepositoryImpl(
    private val client: ShareClient,
    private val storage: ShareStorage,
) : ShareRepository
