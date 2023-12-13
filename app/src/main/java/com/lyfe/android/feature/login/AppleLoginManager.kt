package com.lyfe.android.feature.login

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.lyfe.android.core.common.ui.util.LogUtil
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class AppleLoginManager @Inject constructor(
	@ActivityRetainedScoped val context: Context
) {
	companion object {
		const val TAG = "AppleLoginManager"
	}

	val oAuthProvider: OAuthProvider.Builder = OAuthProvider.newBuilder("apple.com")
	val auth: FirebaseAuth

	init {
		oAuthProvider.scopes = listOf("email", "name")
		oAuthProvider.addCustomParameter("locale", "ko")

		auth = FirebaseAuth.getInstance()
	}

	suspend fun checkPending(): Boolean = suspendCoroutine { continuation ->
		val pending = auth.pendingAuthResult
		if (pending != null) {
			pending.addOnSuccessListener { authResult ->
				LogUtil.d(TAG, "checkPending:onSuccess:$authResult")
				// Get the user profile with authResult.getUser() and
				// authResult.getAdditionalUserInfo(), and the ID
				// token from Apple with authResult.getCredential().
				LogUtil.d(TAG, "Apple Credentials: ${authResult.credential}")
				continuation.resume(true)
			}.addOnFailureListener { e ->
				LogUtil.w(TAG, "checkPending:onFailure $e")
				continuation.resume(false)
			}
		} else {
			LogUtil.d(TAG, "pending: null")
			continuation.resume(false)
		}
	}

	fun signOut(): Boolean {
		auth.signOut().runCatching {
			return false
		}
		return true
	}
}