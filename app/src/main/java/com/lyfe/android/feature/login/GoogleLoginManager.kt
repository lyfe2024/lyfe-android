package com.lyfe.android.feature.login

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.lyfe.android.BuildConfig
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GoogleLoginManager @Inject constructor(
	@ActivityContext private val context: Context
) {
	companion object {
		const val TAG = "GoogleLoginManager"
	}

	private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
		.requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
		.requestServerAuthCode(BuildConfig.GOOGLE_WEB_CLIENT_ID)
		.requestEmail()
		.build()

	private val googleSignInClient = GoogleSignIn.getClient(context, gso)

	fun handleSignInResult(completedTask: Task<GoogleSignInAccount>): String {
		try {
			val authCode = completedTask.getResult(ApiException::class.java)?.serverAuthCode!!
			Log.d(TAG, "Auth Code: $authCode")
			return authCode
		} catch (e: ApiException) {
			Log.w(TAG, "handleSignInResult: error" + e.statusCode)
			throw e
		}
	}

	fun createSignInIntent(): Intent {
		return googleSignInClient.signInIntent
	}

	suspend fun signOut(): Boolean = suspendCoroutine { continuation ->
		googleSignInClient.signOut().addOnCompleteListener {
			continuation.resume(it.isSuccessful)
		}
	}

	fun isLogin(context: Context): Boolean {
		val account = GoogleSignIn.getLastSignedInAccount(context)
		return if (account == null) false else (true)
	}
}