package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.PolicyDataSource
import com.lyfe.android.core.data.model.Terms
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.PolicyRepository
import javax.inject.Inject

class PolicyRepositoryImpl @Inject constructor(
	private val policyDataSource: PolicyDataSource
) : PolicyRepository {
	override suspend fun fetchServiceTerms(): Result<Terms> {
		return policyDataSource.fetchServiceTerms()
	}

	override suspend fun fetchPersonalInfoTerms(): Result<Terms> {
		return policyDataSource.fetchPersonalInfoTerms()
	}
}