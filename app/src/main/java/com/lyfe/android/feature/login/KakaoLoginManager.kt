package com.lyfe.android.feature.login

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.lyfe.android.core.common.ui.util.LogUtil
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoLoginManager @Inject constructor(
	@ActivityRetainedScoped private val context: Context,
	val onTokenReceived: (OAuthToken) -> Unit
) {
	companion object {
		const val TAG = "KakaoLoginManager"
	}

	private val kakaoLoginState by lazy { getKaKaoLoginState() }
	private val kakaoAccountLoginCallback by lazy { getAccountLoginCallback(onTokenReceived) }

	fun startKakaoLogin() {
		when (kakaoLoginState) {
			KaKaoLoginState.KAKAO_TALK_LOGIN -> onKakaoTalkLogin(onTokenReceived)
			KaKaoLoginState.KAKAO_ACCOUNT_LOGIN -> onKakaoAccountLogin()
		}
	}

	private fun getKaKaoLoginState(): KaKaoLoginState =
		if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
			KaKaoLoginState.KAKAO_TALK_LOGIN
		} else {
			KaKaoLoginState.KAKAO_ACCOUNT_LOGIN
		}

	private fun getAccountLoginCallback(onTokenReceived: (OAuthToken) -> Unit): (OAuthToken?, Throwable?) -> Unit {
		return { token, error ->
			if (error != null) {
				LogUtil.e(TAG, "${error.message} 카카오 계정으로 로그인 실패")
				throw error
			} else if (token != null) {
				onTokenReceived(token)
			}
		}
	}

	private fun onKakaoTalkLogin(onTokenReceived: (OAuthToken) -> Unit) {
		UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
			if (error != null) {
				// 카카오톡으로 로그인 실패
				if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
					return@loginWithKakaoTalk
				}
				onKakaoAccountLogin()
			} else if (token != null) {
				onTokenReceived(token)
			}
		}
	}

	private fun onKakaoAccountLogin() {
		UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoAccountLoginCallback)
	}
}

private enum class KaKaoLoginState {
	KAKAO_TALK_LOGIN, KAKAO_ACCOUNT_LOGIN
}