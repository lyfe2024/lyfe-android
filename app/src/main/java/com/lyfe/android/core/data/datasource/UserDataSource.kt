package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.network.model.Result

interface UserDataSource {
	suspend fun checkNicknameDuplicated(nickname: String): Result<CheckNicknameResponse>
}