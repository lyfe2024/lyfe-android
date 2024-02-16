package com.lyfe.android.core.data.network.interceptor

import com.lyfe.android.core.data.datasource.LocalTokenDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
	private val localTokenDataSource: LocalTokenDataSource
) : Interceptor {

	override fun intercept(chain: Interceptor.Chain): Response {
		return runBlocking {
			val accessToken = localTokenDataSource.getAccessToken().first()
			val request = if (accessToken.isNotEmpty()) {
				chain.request().putTokenHeader(accessToken)
			} else {
				chain.request()
			}
			chain.proceed(request)
		}
	}

	private fun Request.putTokenHeader(accessToken: String): Request {
		return this.newBuilder()
			.addHeader(AUTHORIZATION, "Bearer $accessToken")
			.build()
	}

	companion object {
		private const val AUTHORIZATION = "authorization"
	}
}