package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.GetPersonalInfoAgreementsResponse
import com.lyfe.android.core.data.model.GetServiceTermsResponse
import com.lyfe.android.core.data.network.model.Result

interface PolicyRepository {

	suspend fun getServiceTerms(): Result<GetServiceTermsResponse>

	suspend fun getPersonalInfoAgreements(): Result<GetPersonalInfoAgreementsResponse>
}