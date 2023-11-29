package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.GoogleTokenRequest
import com.lyfe.android.core.data.model.GoogleTokenResponse
import com.lyfe.android.core.data.network.model.Result
import kotlinx.coroutines.flow.Flow

interface GoogleRepository {

	suspend fun getAccessToken(request: GoogleTokenRequest): Flow<Result<GoogleTokenResponse>>
}