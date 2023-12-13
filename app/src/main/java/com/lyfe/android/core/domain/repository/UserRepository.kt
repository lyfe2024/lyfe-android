package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.network.model.Result

interface UserRepository {
	suspend fun fetchIsNicknameDuplicated(
		nickname: String
	): Result<CheckNicknameResponse>
}