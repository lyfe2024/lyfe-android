package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.Terms
import com.lyfe.android.core.data.network.model.Result

interface PolicyDataSource {

	suspend fun fetchServiceTerms(): Result<Terms>

	suspend fun fetchPersonalInfoTerms(): Result<Terms>
}