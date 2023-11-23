package com.lyfe.android.feature.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

	companion object {
		const val TAG = "LoginViewModel"
	}

	var uiState by mutableStateOf<LoginUiState>(LoginUiState.IDLE)
		private set

	val kakaoLoginResult = MutableStateFlow<Result<OAuthToken>>(
		Result.failure(Throwable())
	)

	fun kakaoLogin(context: Context) {
		viewModelScope.launch {
			kakaoLoginResult.emit(handleKakaoLogin(context))
		}
	}

	private suspend fun handleKakaoLogin(context: Context): Result<OAuthToken> = runCatching {
		if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
			try {
				UserApiClient.loginWithKakaoTalk(context)
			} catch (error: Throwable) {
				if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
					throw error
				} else {
					error.printStackTrace()
					UserApiClient.loginWithKakaoAccount(context)
				}
			}
		} else {
			UserApiClient.loginWithKakaoAccount(context)
		}
	}

	// 카카오톡으로 로그인 시도
	private suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken =
		suspendCoroutine { continuation ->
			instance.loginWithKakaoTalk(context) { token, error ->
				continuation.resumeTokenOrException(token, error)
			}
		}

	// 카카오 계정으로 로그인 시도
	private suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): OAuthToken =
		suspendCoroutine { continuation ->
			instance.loginWithKakaoAccount(context) { token, error ->
				continuation.resumeTokenOrException(token, error)
			}
		}

	private fun Continuation<OAuthToken>.resumeTokenOrException(token: OAuthToken?, error: Throwable?) {
		if (error != null) {
			resumeWithException(error)
		} else if (token != null) {
			Log.d(TAG, "KAKAO_REFRESH_TOKEN " + token.refreshToken)
			Log.d(TAG, "KAKAO_ACCESS_TOKEN " + token.accessToken)
			resume(token)
		} else {
			resumeWithException(RuntimeException("Can't Receive Kakao Access Token"))
		}
	}
}