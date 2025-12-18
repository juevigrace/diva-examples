package com.diva.auth.data.shared

import com.diva.auth.database.shared.AuthStorage
import com.diva.models.LocalSource

class AuthLocalSourceImpl(override val source: AuthStorage) : LocalSource<AuthStorage>(source)
