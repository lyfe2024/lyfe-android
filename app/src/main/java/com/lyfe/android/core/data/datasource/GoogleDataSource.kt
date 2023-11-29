package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GoogleTokenRequest
import com.lyfe.android.core.data.model.GoogleTokenResponse
import com.lyfe.android.core.data.network.model.Result

interface GoogleDataSource {

	suspend fun getAccessToken(request: GoogleTokenRequest): Result<GoogleTokenResponse>

}