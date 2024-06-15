package com.lyfe.android.core.data.network.interceptor

import android.util.Log
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
		val token: String? = runBlocking {
			tokenManager.getAccessToken().first()
		}

		val request = chain.request().newBuilder().apply {
			if (token != null) {
				Log.i("Token", token)
				this.header(HEADER_AUTHORIZATION, "$HEADER_AUTHORIZATION_TYPE $token")
			}
		}.build()

		val response = chain.proceed(request)
		val responseJson = response.extractResponseJson()

		// result 키 존재 유무에 따른 json 생성
		val resultPayload = if (responseJson.has(RESULT_KEY)) {
			JSONObject(responseJson[RESULT_KEY].toString())
		} else {
			responseJson
		}

		// page 키 존재 유무에 따른 key-value 추가
		val dataPayload = resultPayload.apply {
			if (responseJson.has(PAGE_KEY)) {
				put(PAGE_KEY, responseJson[PAGE_KEY])
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