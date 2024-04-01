package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.policy.ServicePolicyResponse
import com.lyfe.android.core.data.model.policy.UserInfoPolicyResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.GET

interface PolicyService {

	@GET("/v1/policys/TERM")
	suspend fun fetchServicePolicy(): Result<ServicePolicyResponse>

	@GET("/v1/policys/PERSONAL_INFO_AGREEMENT")
	suspend fun fetchUserInfoPolicy(): Result<UserInfoPolicyResponse>
}