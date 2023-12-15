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
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GoogleLoginManager @Inject constructor(
	@ActivityRetainedScoped private val context: Context
) {
	companion object {
		const val TAG = "GoogleLoginManager"
	}

	private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
		.requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
		.requestEmail()
		.build()

	private val googleSignInClient = GoogleSignIn.getClient(context, gso)

	fun handleSignInResult(completedTask: Task<GoogleSignInAccount>): String? {
		try {
			return completedTask.getResult(ApiException::class.java)?.idToken
		} catch (e: ApiException) {
			LogUtil.w(TAG, "handleSignInResult: error" + e.statusCode)
			throw e
		}
	}

	fun createSignInIntent(): Intent = googleSignInClient.signInIntent

	suspend fun signOut(): Boolean = suspendCoroutine { continuation ->
		googleSignInClient.signOut().addOnCompleteListener {
			continuation.resume(it.isSuccessful)
		}
	}

	fun isLogin(context: Context): Boolean {
		val account = GoogleSignIn.getLastSignedInAccount(context)
		return if (account == null) false else true
	}
}