package com.diva.models.database.social.interaction

import com.diva.models.social.share.ShareType

data class ShareEntity(
    val interactionId: String,
    val shareType: ShareType,
    val caption: String,
)
