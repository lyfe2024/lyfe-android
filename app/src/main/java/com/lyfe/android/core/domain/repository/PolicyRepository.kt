package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.Terms
import com.lyfe.android.core.data.network.model.Result

interface PolicyRepository {

	suspend fun fetchServiceTerms(): Result<Terms>

	suspend fun fetchPersonalInfoTerms(): Result<Terms>
}