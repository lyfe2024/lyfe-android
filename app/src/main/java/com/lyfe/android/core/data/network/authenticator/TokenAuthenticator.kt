package com.lyfe.android.core.data.network.authenticator

import com.lyfe.android.core.data.datasource.LocalTokenDataSource
import com.lyfe.android.core.data.model.ReissueTokenRequest
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Named

class TokenAuthenticator @Inject constructor(
	private val localTokenDataSource: LocalTokenDataSource,
	@Named("authenticator")	private val authService: AuthService
) : Authenticator {

	// AtomicBoolean in order to avoid race condition
	private var tokenRefreshInProgress = AtomicBoolean(false)
	private var request: Request? = null

	override fun authenticate(route: Route?, response: Response): Request? {
		// The access token is expired. Refresh the credentials.
		synchronized(this) {
			// Make sure only one coroutine refreshes the token at a time.
			return runBlocking {
				request = null
				// 여러 API요청으로 인해 중복으로 재발행되는 현상 방지
				if (!tokenRefreshInProgress.get()) {
					tokenRefreshInProgress.set(true)
					// Token Refresh
					val isRefreshed = refreshToken()

					tokenRefreshInProgress.set(false)

					if (isRefreshed) {
						request = buildRequest(response.request.newBuilder())
					}
				} else {
					// 현재 토큰 재발행 진행중인 요청이 끝날때까지 대기
					waitForRefresh(response)
				}

				if (responseCount(response) >= MAX_REQUEST_COUNT) {
					return@runBlocking null
				} else {
					return@runBlocking request
				}
			}
		}
	}

	private suspend fun refreshToken(): Boolean {
		val refreshToken = runBlocking {
			localTokenDataSource.getRefreshToken().first()
		}

		val newTokenResult = authService.reissueToken(ReissueTokenRequest(refreshToken))
		if (newTokenResult is Result.Success) {
			val result = newTokenResult.body?.result
			val newAccessToken = result?.accessToken
			val newRefreshToken = result?.refreshToken
			if (newAccessToken != null && newRefreshToken != null) {
				// Update the access token in your storage.
				localTokenDataSource.updateAccessToken(newAccessToken)
				localTokenDataSource.updateRefreshToken(newRefreshToken)

				return true
			}
		}
		return false
	}

	private suspend fun waitForRefresh(response: Response) {
		while (tokenRefreshInProgress.get()) {
			delay(REFRESH_WAIT_DELAY)
		}
		request = buildRequest(response.request.newBuilder())
	}

	private fun responseCount(response: Response?): Int {
		var result = 1
		while (response?.priorResponse != null && result <= MAX_REQUEST_COUNT) {
			result++
		}
		return result
	}

	private suspend fun buildRequest(requestBuilder: Request.Builder): Request {
		val accessToken = localTokenDataSource.getAccessToken().first()
		return requestBuilder
			.header(HEADER_AUTHORIZATION, HEADER_AUTHORIZATION_TYPE + accessToken)
			.build()
	}

	companion object {
		const val HEADER_AUTHORIZATION = "Authorization"
		const val HEADER_AUTHORIZATION_TYPE = "Bearer "
		private const val MAX_REQUEST_COUNT = 3
		private const val REFRESH_WAIT_DELAY = 200L
	}
}