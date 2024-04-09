package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.Terms
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.PolicyService
import javax.inject.Inject

class PolicyRemoteDataSource @Inject constructor(
	private val policyService: PolicyService
) : PolicyDataSource {
	override suspend fun fetchServiceTerms(): Result<Terms> {
		return policyService.fetchServiceTerms()
	}

	override suspend fun fetchPersonalInfoTerms(): Result<Terms> {
		return policyService.fetchPersonalInfoTerms()
	}
}