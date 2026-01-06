package com.diva.verification.database

import com.diva.models.server.UserVerification
import io.github.juevigrace.diva.database.Storage

interface VerificationStorage : Storage<UserVerification>
