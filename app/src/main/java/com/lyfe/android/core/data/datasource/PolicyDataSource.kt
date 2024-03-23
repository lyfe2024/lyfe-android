package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetPersonalInfoAgreementsResponse
import com.lyfe.android.core.data.model.GetServiceTermsResponse
import com.lyfe.android.core.data.network.model.Result

interface PolicyDataSource {

	suspend fun getServiceTerms(): Result<GetServiceTermsResponse>

	suspend fun getPersonalInfoAgreementsTerms(): Result<GetPersonalInfoAgreementsResponse>
}