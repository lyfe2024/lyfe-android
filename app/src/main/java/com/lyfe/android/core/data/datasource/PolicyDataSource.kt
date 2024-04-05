package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.Terms
import com.lyfe.android.core.data.network.model.Result

interface PolicyDataSource {

	suspend fun getServiceTerms(): Result<Terms>

	suspend fun getPersonalInfoAgreementsTerms(): Result<Terms>
}