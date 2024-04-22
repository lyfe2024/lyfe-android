package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.Terms
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.GET

interface PolicyService {

	// 서비스 이용약관 API
	@GET("/v1/policy/term")
	suspend fun fetchServiceTerms(): Result<Terms>

	// 개인 정보 수집 동의 API
	@GET("/v1/policy/PERSONAL_INFO_AGREEMENT")
	suspend fun fetchPersonalInfoTerms(): Result<Terms>
}