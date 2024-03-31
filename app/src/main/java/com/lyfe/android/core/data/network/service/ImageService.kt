package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.GetImageUploadUrlResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

	// 이미지 업로드용 url 요청하기
	@GET("/v1/images/get-upload-url")
	suspend fun getUploadUrl(
		@Query("format") format: String,
		@Query("path") path: String
	): Result<GetImageUploadUrlResponse>
}