package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.PolicyDataSource
import com.lyfe.android.core.data.model.Terms
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.PolicyRepository
import javax.inject.Inject

class PolicyRepositoryImpl @Inject constructor(
	private val policyDataSource: PolicyDataSource
) : PolicyRepository {
	override suspend fun getServiceTerms(): Result<Terms> {
		return policyDataSource.getServiceTerms()
	}

	override suspend fun getPersonalInfoAgreements(): Result<Terms> {
		return policyDataSource.getPersonalInfoAgreementsTerms()
	}
}