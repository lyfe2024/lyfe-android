package com.lyfe.android.core.data.network.authenticator

import com.lyfe.android.BuildConfig.BASE_URL
import com.lyfe.android.core.data.datasource.LocalTokenDataSource
import com.lyfe.android.core.data.model.ReissueTokenResponse
import com.lyfe.android.core.data.network.adapter.ResultCallAdapterFactory
import com.lyfe.android.core.data.network.converter.asConverterFactory
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.AuthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
	private val localTokenDataSource: LocalTokenDataSource
): Authenticator {

	private companion object {
		private const val ConnectTimeout = 15L
		private const val WriteTimeout = 20L
		private const val ReadTimeout = 15L
		private val contentType = "application/json".toMediaType()
	}

	override fun authenticate(route: Route?, response: Response): Request? {
		if (response.code == 401) {
			val refreshToken = runBlocking {
				localTokenDataSource.getRefreshToken().first()
			}
			// The access token is expired. Refresh the credentials.
			synchronized(this) {
				// Make sure only one coroutine refreshes the token at a time.
				return runBlocking {
					try {
						val newTokenResult = getNewToken(refreshToken)
						if (newTokenResult is Result.Success) {
							// Update the access token in your storage.
							localTokenDataSource.updateRefreshToken(newTokenResult.body!!.result.refreshToken)
							localTokenDataSource.updateAccessToken(newTokenResult.body.result.accessToken)
						}
						return@runBlocking response.request.newBuilder()
							.header("Authorization", "Bearer ${localTokenDataSource.getAccessToken()}")
							.build()

					} catch (e: Exception) {
						// The refresh process failed. Give up on retrying the request.
						return@runBlocking null
					}
				}
			}
		}
		return response.request
	}

	private suspend fun getNewToken(refreshToken: String): Result<ReissueTokenResponse> {
		val jsonConfig = Json { isLenient = true }

		val okHttpClient = OkHttpClient.Builder()
			.connectTimeout(ConnectTimeout, TimeUnit.SECONDS)
			.writeTimeout(WriteTimeout, TimeUnit.SECONDS)
			.readTimeout(ReadTimeout, TimeUnit.SECONDS)
			.addInterceptor(
				HttpLoggingInterceptor().apply {
					level = HttpLoggingInterceptor.Level.BODY
				}
			)
			.build()

		val retrofit = Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(jsonConfig.asConverterFactory(contentType))
			.addCallAdapterFactory(ResultCallAdapterFactory())
			.client(okHttpClient)
			.build()

		val service = retrofit.create(AuthService::class.java)
		return service.reissueToken(mapOf("token" to refreshToken))
	}
}