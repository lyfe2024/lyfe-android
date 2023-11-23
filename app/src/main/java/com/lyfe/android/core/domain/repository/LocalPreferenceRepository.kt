package com.lyfe.android.core.domain.repository

interface LocalPreferenceRepository {

	fun getAccessToken(): String?

	fun updateAccessToken(accessToken: String)

	fun removeAccessToken()
}