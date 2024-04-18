package com.lyfe.android.core.data.network.interceptor

import com.lyfe.android.core.data.network.token.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
	private val tokenManager: TokenManager
) : Interceptor {

	override fun intercept(chain: Interceptor.Chain): Response {
		val token: String = runBlocking {
			tokenManager.getAccessToken().first()
		}

		val request = chain.request().newBuilder().apply {
			this.header(HEADER_AUTHORIZATION, "$HEADER_AUTHORIZATION_TYPE $token")
		}.build()

		val response = chain.proceed(request)
		val responseJson = response.extractResponseJson()
		val resultPayload = if (responseJson.has(RESULT_KEY)) responseJson[RESULT_KEY] else responseJson
		val pagePayload = if (responseJson.has(PAGE_KEY)) responseJson[PAGE_KEY] else responseJson

		val dataPayload = JSONObject().apply {
			put(RESULT_KEY, resultPayload.toString())

			if (pagePayload != BASE_JSON_FORMAT) {
				put(PAGE_KEY, pagePayload)
			}
		}

		return response.newBuilder()
			.body(dataPayload.toString().toResponseBody())
			.build()
	}

	private fun Response.extractResponseJson(): JSONObject {
		val jsonString: String = this.body?.string() ?: BASE_JSON_FORMAT
		return runCatching {
			JSONObject(jsonString)
		}.getOrElse {
			JSONObject(BASE_JSON_FORMAT)
		}
	}

	companion object {
		private const val HEADER_AUTHORIZATION = "Authorization"
		private const val HEADER_AUTHORIZATION_TYPE = "Bearer"
		private const val RESULT_KEY = "result"
		private const val PAGE_KEY = "page"
		private const val BASE_JSON_FORMAT = "{}"
	}
}