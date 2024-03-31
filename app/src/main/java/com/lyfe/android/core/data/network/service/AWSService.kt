package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.GetUserInfoResponse
import com.lyfe.android.core.data.network.model.Result
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface AWSService {

	// 이미지 업로드하기
	@Multipart
	@PUT("/{dev}/{path}/{fileName}")
	suspend fun putImage(
		@Path("dev") dev: String,
		@Path("path") path: String,
		@Path("fileName") fileName: String,
		@QueryMap queryMap: HashMap<String, String>,
		@Part body: MultipartBody.Part
	): Result<GetUserInfoResponse>
}