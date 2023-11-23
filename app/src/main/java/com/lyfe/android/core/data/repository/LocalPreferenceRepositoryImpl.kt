package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.LocalPreferenceDataSource
import com.lyfe.android.core.domain.repository.LocalPreferenceRepository
import javax.inject.Inject

class LocalPreferenceRepositoryImpl @Inject constructor(
	private val localPreferencesUserDataSource: LocalPreferenceDataSource,
) : LocalPreferenceRepository {

	override fun getAccessToken(): String? {
		return localPreferencesUserDataSource.getAccessToken()
	}

	override fun updateAccessToken(accessToken: String) {
		localPreferencesUserDataSource.updateAccessToken(accessToken)
	}

	override fun removeAccessToken() {
		localPreferencesUserDataSource.removeAccessToken()
	}

}