package com.diva.auth.data.shared

import com.diva.auth.database.shared.AuthStorage
import com.diva.models.Repository

interface AuthRepository : Repository<AuthStorage> {
}