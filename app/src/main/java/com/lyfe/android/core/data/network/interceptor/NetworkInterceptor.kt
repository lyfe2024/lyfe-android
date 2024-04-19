package com.lyfe.android.core.data.network.interceptor

import android.util.Log
import com.google.gson.JsonObject
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
//		val token: String? = runBlocking {
//			tokenManager.getAccessToken().first()
//		}

		val token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJQZXJtYW5lbnRUb2tlbiIsImV4cCI6NDg2NTQwNzg1MiwiUGVybWFuZW50VG9rZW4iOiJzeXN0ZW1NYW5hZ2VyQEdPT0dMRSJ9.R-QCjTpPSuE6T_p7zxrLU_KU09Uzn_5UZ4RaA26b1_PNQAdwwheZZR3i0wYXln86cR7-MJHZ8aXFmKuA-5DEbA"
		val request = chain.request().newBuilder().apply {
			this.header(HEADER_AUTHORIZATION, "$HEADER_AUTHORIZATION_TYPE $token")
		}.build()

		val response = chain.proceed(request)
		val responseJson = response.extractResponseJson()
		val resultPayload = if (responseJson.has(RESULT_KEY)) responseJson[RESULT_KEY] else responseJson

		return response.newBuilder()
			.body(resultPayload.toString().toResponseBody())
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
		private const val BASE_JSON_FORMAT = "{}"
	}
}