package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.model.ServicePolicyResult
import com.lyfe.android.core.model.UserInfoPolicyResult
import kotlinx.coroutines.flow.Flow
import com.lyfe.android.core.data.network.model.Result as Result

interface PolicyRepository {

	fun fetchServicePolicy(): Flow<Result<ServicePolicyResult>>

	fun fetchUserInfoPolicy(): Flow<Result<UserInfoPolicyResult>>
}