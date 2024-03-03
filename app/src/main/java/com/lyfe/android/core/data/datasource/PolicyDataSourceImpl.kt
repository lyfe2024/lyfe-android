package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetPersonalInfoAgreementsResponse
import com.lyfe.android.core.data.model.GetServiceTermsResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.PolicyService
import javax.inject.Inject

class PolicyDataSourceImpl @Inject constructor(
	private val policyService: PolicyService
) : PolicyDataSource {
	override suspend fun getServiceTerms(): Result<GetServiceTermsResponse> {
		return policyService.getServiceTerms()
	}

	override suspend fun getPersonalInfoAgreementsTerms(): Result<GetPersonalInfoAgreementsResponse> {
		return policyService.getPersonalInfoAgreements()
	}
}