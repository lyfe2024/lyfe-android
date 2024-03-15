package com.lyfe.android.core.data.datasource

import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {

	fun getSocialType(): Flow<String>

	suspend fun updateSocialType(socialType: String)
}