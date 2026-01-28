package com.diva.media.data

import com.diva.database.media.MediaStorage

class MediaServiceImpl(
    private val storage: MediaStorage,
) : MediaService