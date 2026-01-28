package com.diva.media.data

import com.diva.database.media.tag.TagStorage

class TagServiceImpl(
    private val storage: TagStorage,
) : TagService