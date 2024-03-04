package com.lyfe.android.core.data.network.interceptor

import com.lyfe.android.core.data.datasource.LocalTokenDataSource
import com.lyfe.android.core.data.network.authenticator.TokenAuthenticator.Companion.HEADER_AUTHORIZATION
import com.lyfe.android.core.data.network.authenticator.TokenAuthenticator.Companion.HEADER_AUTHORIZATION_TYPE
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
		val accessToken = runBlocking {
			localTokenDataSource.getAccessToken().first()
		}
		val request = if (accessToken.isNotEmpty()) {
			chain.request().putTokenHeader(accessToken = accessToken)
		} else {
			chain.request()
		}

		return chain.proceed(request)
	}

	private fun Request.putTokenHeader(accessToken: String): Request {
		return this.newBuilder()
			.addHeader(HEADER_AUTHORIZATION, HEADER_AUTHORIZATION_TYPE + accessToken)
			.build()
	}
}