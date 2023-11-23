package com.lyfe.android.feature.login

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class KakaoLoginManager @Inject constructor(
	@ActivityContext private val context: Context
) {
	companion object {
		const val TAG = "KakaoLoginManager"
	}

	private lateinit var kakaoLoginState: KaKaoLoginState
	private lateinit var kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit

	fun startKakaoLogin(
		updateSocialToken: (OAuthToken) -> Unit
	) {
		kakaoLoginState = getKaKaoLoginState()
		kakaoLoginCallback = getLoginCallback(updateSocialToken)

		when (kakaoLoginState) {
			KaKaoLoginState.KAKAO_TALK_LOGIN -> onKakaoTalkLogin(updateSocialToken)
			KaKaoLoginState.KAKAO_ACCOUNT_LOGIN -> onKakaoAccountLogin()
		}
	}

	private fun getKaKaoLoginState(): KaKaoLoginState =
		if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
			KaKaoLoginState.KAKAO_TALK_LOGIN
		} else {
			KaKaoLoginState.KAKAO_ACCOUNT_LOGIN
		}

	private fun getLoginCallback(updateSocialToken: (OAuthToken) -> Unit): (OAuthToken?, Throwable?) -> Unit {
		return { token, error ->
			if (error != null) {
				Log.e(TAG, "${error.message} 카카오 계정으로 로그인 실패")
				throw error
			} else if (token != null) {
				updateSocialToken(token)
			}
		}
	}

	private fun onKakaoTalkLogin(updateSocialToken: (OAuthToken) -> Unit) {
		UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
			if (error != null) {
				// 카카오톡으로 로그인 실패
				if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
					return@loginWithKakaoTalk
				}
				onKakaoAccountLogin()
			} else if (token != null) {
				updateSocialToken(token)
			}
		}
	}

	private fun onKakaoAccountLogin() {
		UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoLoginCallback)
	}
}

enum class KaKaoLoginState {
	KAKAO_TALK_LOGIN, KAKAO_ACCOUNT_LOGIN
}