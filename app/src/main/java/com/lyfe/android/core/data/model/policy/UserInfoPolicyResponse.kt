package com.lyfe.android.core.data.model.policy

import com.lyfe.android.core.model.UserInfoPolicyResult
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoPolicyResponse(
	val result: UserInfoPolicyResult
)