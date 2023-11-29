package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GoogleTokenRequest(
	private val grant_type: String,
	private val client_id: String,
	private val client_secret: String,
	private val code: String
)
