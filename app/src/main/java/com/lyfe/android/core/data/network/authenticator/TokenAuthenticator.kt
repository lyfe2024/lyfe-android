package com.lyfe.android.core.data.network.authenticator

import com.lyfe.android.core.data.datasource.LocalTokenDataSource
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.AuthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject
import javax.inject.Named

class TokenAuthenticator @Inject constructor(
	private val localTokenDataSource: LocalTokenDataSource,
	@Named("authenticator")	private val authService: AuthService
) : Authenticator {

	override fun authenticate(route: Route?, response: Response): Request {
		if (response.code == HTTP_UNAUTHORIZED) {
			val token = runBlocking {
				localTokenDataSource.getRefreshToken().first()
			}
			// The access token is expired. Refresh the credentials.
			synchronized(this) {
				// Make sure only one coroutine refreshes the token at a time.
				return runBlocking {
					val newTokenResult = authService.reissueToken(mapOf("token" to token))
					if (newTokenResult is Result.Success) {
						val accessToken = newTokenResult.body!!.result.accessToken
						val refreshToken = newTokenResult.body.result.refreshToken
						// Update the access token in your storage.
						localTokenDataSource.updateAccessToken(accessToken)
						localTokenDataSource.updateRefreshToken(refreshToken)
						return@runBlocking response.request.newBuilder()
							.header("Authorization", "Bearer $accessToken")
							.build()
					} else {
						return@runBlocking response.request
					}
				}
			}
		}
		return response.request
	}
}