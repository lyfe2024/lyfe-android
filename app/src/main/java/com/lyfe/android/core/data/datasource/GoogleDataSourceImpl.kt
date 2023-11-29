package com.lyfe.android.core.data.datasource

import android.util.Log
import com.lyfe.android.core.data.model.GoogleTokenRequest
import com.lyfe.android.core.data.model.GoogleTokenResponse
import com.lyfe.android.core.data.network.di.NetworkModule
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.GoogleService
import javax.inject.Inject

class GoogleDataSourceImpl @Inject constructor() : GoogleDataSource {

	private val okHttpClient = NetworkModule.providesLyfeOkHttpClient()
	private val retrofit = NetworkModule.providesGoogleRetrofit(okHttpClient)

	private val googleService = retrofit.create(GoogleService::class.java)

	override suspend fun getAccessToken(request: GoogleTokenRequest): Result<GoogleTokenResponse> {
		Log.d("Google Request", request.toString())
		return googleService.getAccessToken(request.toString())
	}
}