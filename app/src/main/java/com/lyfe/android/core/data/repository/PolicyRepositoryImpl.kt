package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.PolicyDataSource
import com.lyfe.android.core.data.model.GetPersonalInfoAgreementsResponse
import com.lyfe.android.core.data.model.GetServiceTermsResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.PolicyRepository
import javax.inject.Inject

class PolicyRepositoryImpl @Inject constructor(
	private val policyDataSource: PolicyDataSource
) : PolicyRepository {
	override suspend fun getServiceTerms(): Result<GetServiceTermsResponse> {
		return policyDataSource.getServiceTerms()
	}

	override suspend fun getPersonalInfoAgreements(): Result<GetPersonalInfoAgreementsResponse> {
		return policyDataSource.getPersonalInfoAgreementsTerms()
	}
}