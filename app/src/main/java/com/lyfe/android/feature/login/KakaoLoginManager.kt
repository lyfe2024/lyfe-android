package com.lyfe.android.feature.login

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.lyfe.android.core.common.ui.util.LogUtil

object KakaoLoginManager {

	private const val TAG = "KakaoLoginManager"

	fun startKakaoLogin(context: Context, onTokenReceived: (OAuthToken) -> Unit, onFailure: (Throwable?) -> Unit) {
		when (getKaKaoLoginState(context)) {
			KaKaoLoginState.KAKAO_TALK_LOGIN -> onKakaoTalkLogin(context, onTokenReceived, onFailure)
			KaKaoLoginState.KAKAO_ACCOUNT_LOGIN -> onKakaoAccountLogin(context, onTokenReceived, onFailure)
			KaKaoLoginState.KAKAO_LOGIN_STATE_NONE -> {}
		}
	}

	private fun getKaKaoLoginState(context: Context): KaKaoLoginState =
		if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
			KaKaoLoginState.KAKAO_TALK_LOGIN
		} else {
			KaKaoLoginState.KAKAO_ACCOUNT_LOGIN
		}

	private fun getAccountLoginCallback(
		onTokenReceived: (OAuthToken) -> Unit,
		onFailure: (Throwable?) -> Unit
	): (OAuthToken?, Throwable?) -> Unit {
		return { token, error ->
			if (error != null) {
				LogUtil.e(TAG, "${error.message} 카카오 계정으로 로그인 실패")
				onFailure(error)
			} else if (token != null) {
				onTokenReceived(token)
			}
		}
	}

	private fun onKakaoTalkLogin(
		context: Context,
		onTokenReceived: (OAuthToken) -> Unit,
		onFailure: (Throwable?) -> Unit
	) {
		UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
			if (error != null) {
				// 카카오톡으로 로그인 실패
				if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
					return@loginWithKakaoTalk
				}
				onKakaoAccountLogin(context, onTokenReceived, onFailure)
			} else if (token != null) {
				onTokenReceived(token)
			}
		}
	}

	private fun onKakaoAccountLogin(
		context: Context,
		onTokenReceived: (OAuthToken) -> Unit,
		onFailure: (Throwable?) -> Unit
	) {
		UserApiClient.instance.loginWithKakaoAccount(
			context = context,
			callback = getAccountLoginCallback(onTokenReceived, onFailure)
		)
	}
}

private enum class KaKaoLoginState {
	KAKAO_TALK_LOGIN, KAKAO_ACCOUNT_LOGIN, KAKAO_LOGIN_STATE_NONE
}