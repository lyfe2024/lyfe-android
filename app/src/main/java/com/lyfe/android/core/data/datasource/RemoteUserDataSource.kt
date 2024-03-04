package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.network.model.Result

interface RemoteUserDataSource {
	suspend fun checkNicknameDuplicated(nickname: String): Result<CheckNicknameResponse>
}