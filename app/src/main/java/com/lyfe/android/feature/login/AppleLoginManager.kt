package com.lyfe.android.feature.login

import android.app.Activity
import android.content.Context
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.lyfe.android.core.common.ui.util.LogUtil
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object AppleLoginManager {

	private const val TAG = "AppleLoginManager"

	private val oAuthProvider: OAuthProvider.Builder by lazy { OAuthProvider.newBuilder("apple.com") }
	private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

	init {
		oAuthProvider.scopes = listOf("email", "name")
		oAuthProvider.addCustomParameter("locale", "ko")
	}

	suspend fun isLogin(): Boolean = suspendCoroutine { continuation ->
		val pendingAuthResult = auth.pendingAuthResult
		if (pendingAuthResult != null) {
			pendingAuthResult.addOnSuccessListener { authResult ->
				LogUtil.d(TAG, "checkPending:onSuccess:$authResult")
				// 현재 애플 로그인 되어 있는 상태 or 로그인 시도 중
				LogUtil.d(TAG, "Apple Credentials: ${authResult.credential}")
				continuation.resume(true)
			}.addOnFailureListener { e ->
				LogUtil.w(TAG, "checkPending:onFailure $e")
				continuation.resumeWithException(e)
			}
		} else {
			LogUtil.d(TAG, "pending: null")
			continuation.resume(false)
		}
	}

	fun signOut(
		onFailure: (Throwable?) -> Unit,
		onSuccess: () -> Unit
	) {
		auth.addAuthStateListener {
			if (it.currentUser == null) {
				// Logout Complete
				LogUtil.i(TAG, "로그아웃 성공.")
				onSuccess()
			}
		}
		try {
			// 로그아웃
			auth.signOut()
		} catch (e: FirebaseException) {
			LogUtil.e(TAG, "로그아웃 실패. ${e.message ?: ""}")
			onFailure(e)
		}
	}

	fun signIn(
		context: Context,
		onResult: (AuthResult) -> Unit,
		onError: (Exception) -> Unit
	) {
		auth.startActivityForSignInWithProvider(context as Activity, oAuthProvider.build())
			.addOnSuccessListener { authResult ->
				onResult(authResult)
			}
			.addOnFailureListener {
				onError(it)
			}
	}
}