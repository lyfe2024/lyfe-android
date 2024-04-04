package com.lyfe.android.core.data.network.interceptor

import com.lyfe.android.core.data.network.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
	private val tokenManager: TokenManager,
) : Interceptor {

	override fun intercept(chain: Interceptor.Chain): Response {
		val token: String? = runBlocking {
			tokenManager.getAccessToken().first()
		}

		val request = chain.request().newBuilder().apply {
			if (token != null) {
				this.header(HEADER_AUTHORIZATION, "$HEADER_AUTHORIZATION_TYPE $token")
			}
		}.build()

		val response = chain.proceed(request)
		val responseJson = response.extractResponseJson()
		val dataPayload = if(responseJson.has(RESULT_KEY)) responseJson[RESULT_KEY] else responseJson

		return response.newBuilder()
			.body(dataPayload.toString().toResponseBody())
			.build()
	}

	private fun Response.extractResponseJson(): JSONObject {
		val jsonString: String = this.body?.string() ?: BASE_JSON_FORMAT
		return try {
			JSONObject(jsonString)
		} catch(exception: Exception) {
			throw Exception(EXTRACT_JSON_ERROR)
		}
	}

	companion object {
		private const val HEADER_AUTHORIZATION = "Authorization"
		private const val HEADER_AUTHORIZATION_TYPE = "Bearer"
		private const val RESULT_KEY = "result"
		private const val BASE_JSON_FORMAT = "{}"
		private const val EXTRACT_JSON_ERROR = "No Json Format"
	}
}