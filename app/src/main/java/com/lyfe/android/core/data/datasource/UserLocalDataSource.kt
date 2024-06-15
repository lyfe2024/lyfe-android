package com.lyfe.android.core.data.datasource

import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

	fun getSocialType(): Flow<String>

	suspend fun updateSocialType(socialType: String)

	suspend fun deleteAllUserData()
}