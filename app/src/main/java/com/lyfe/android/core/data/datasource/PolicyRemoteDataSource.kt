package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.Terms
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.PolicyService
import javax.inject.Inject

class PolicyRemoteDataSource @Inject constructor(
	private val policyService: PolicyService
) : PolicyDataSource {
	override suspend fun getServiceTerms(): Result<Terms> {
		return policyService.getServiceTerms()
	}

	override suspend fun getPersonalInfoAgreementsTerms(): Result<Terms> {
		return policyService.getPersonalInfoAgreements()
	}
}