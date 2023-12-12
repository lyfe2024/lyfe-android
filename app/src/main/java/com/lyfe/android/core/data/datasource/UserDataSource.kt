package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResult
import com.lyfe.android.core.data.model.Response
import com.lyfe.android.core.data.network.model.Result

interface UserDataSource {
	suspend fun fetchIsNicknameDuplicated(
		nickname: String
	): Result<Response<CheckNicknameResult>>
}