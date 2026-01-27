package com.diva.permissions.api.client

import io.github.juevigrace.diva.network.client.DivaClient

class PermissionsClientImpl(
    private val client: DivaClient,
) : PermissionsClient
