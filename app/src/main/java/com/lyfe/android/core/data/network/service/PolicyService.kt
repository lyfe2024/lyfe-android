package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.GetPersonalInfoAgreementsResponse
import com.lyfe.android.core.data.model.GetServiceTermsResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.GET

interface PolicyService {

	// 서비스 이용약관 API
	@GET("/v1/policys/TERM")
	suspend fun getServiceTerms(): Result<GetServiceTermsResponse>

	// 개인 정보 수집 동의 API
	@GET("/v1/policys/PERSONAL_INFO_AGREEMENT")
	suspend fun getPersonalInfoAgreements(): Result<GetPersonalInfoAgreementsResponse>
}