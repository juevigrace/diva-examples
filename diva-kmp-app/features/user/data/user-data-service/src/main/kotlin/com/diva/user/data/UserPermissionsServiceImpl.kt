package com.diva.user.data

import com.diva.database.user.permissions.UserPermissionsStorage

class UserPermissionsServiceImpl(
    private val storage: UserPermissionsStorage,
) : UserPermissionsService
