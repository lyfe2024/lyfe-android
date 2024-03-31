package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DeleteAccountResponse(
	val result: RevokeResult
)

@Serializable
data class RevokeResult(
	val message: String
)