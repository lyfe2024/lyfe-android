package com.lyfe.android.feature.login

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.lyfe.android.BuildConfig
import com.lyfe.android.core.common.ui.util.LogUtil
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object GoogleLoginManager {

	private const val TAG = "GoogleLoginManager"

	private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
		.requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
		.requestServerAuthCode(BuildConfig.GOOGLE_WEB_CLIENT_ID)
		.requestEmail()
		.build()

	fun handleSignInResult(completedTask: Task<GoogleSignInAccount>): GoogleSignInAccount {
		try {
			return completedTask.getResult(ApiException::class.java)
		} catch (e: ApiException) {
			LogUtil.w(TAG, "handleSignInResult: error" + e.statusCode)
			throw e
		}
	}

	fun createSignInIntent(context: Context): Intent = GoogleSignIn.getClient(context, gso).signInIntent

	fun signOut(
		context: Context,
		onFailure: (Throwable?) -> Unit,
		onSuccess: () -> Unit
	) {
		if (isLogin(context)) {
			val googleSignInClient = GoogleSignIn.getClient(context, gso)
			googleSignInClient.signOut().addOnCompleteListener {
				if (it.isSuccessful) {
					LogUtil.i(TAG, "로그아웃 성공.")
					onSuccess()
				} else {
					LogUtil.e(TAG, "로그아웃 실패. ${it.exception?.message ?: ""}")
					onFailure(it.exception)
				}
			}
		} else {
			onSuccess()
		}
	}

	private fun isLogin(context: Context): Boolean {
		val account = GoogleSignIn.getLastSignedInAccount(context)
		return account != null
	}
}