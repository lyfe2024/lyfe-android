package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthUserRequest(
	val socialType: String,
	val authorizationCode: String,
	val idToken: String,
	val fcmToken: String
)