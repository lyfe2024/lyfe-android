package com.lyfe.android.core.data.datasource

interface LocalPreferenceDataSource {

	fun getAccessToken(): String?

	fun updateAccessToken(accessToken: String)

	fun removeAccessToken()
}