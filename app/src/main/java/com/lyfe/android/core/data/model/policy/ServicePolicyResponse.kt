package com.lyfe.android.core.data.model.policy

import com.lyfe.android.core.model.ServicePolicyResult
import kotlinx.serialization.Serializable


@Serializable
data class ServicePolicyResponse(
	val result: ServicePolicyResult
)