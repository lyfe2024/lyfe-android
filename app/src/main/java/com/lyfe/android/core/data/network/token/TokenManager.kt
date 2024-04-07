package com.lyfe.android.core.data.network.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenManager @Inject constructor(
	private val dataStore: DataStore<Preferences>
) : TokenManagerFunc {

	companion object {
		private val ACCESS_TOKEN_KEY = stringPreferencesKey("accessToken")
		private val REFRESH_TOKEN_KEY = stringPreferencesKey("refreshToken")
		private val SIGN_UP_TOKEN_KEY = stringPreferencesKey("signUpToken")
		private val SIGN_UP_TOKEN_EXPIRATION_TIME_KEY = longPreferencesKey("signUpTokenExpirationTime")
		private val ACCESS_TOKEN_EXPIRATION_TIME_KEY = longPreferencesKey("accessTokenExpirationTime")
		private val REFRESH_TOKEN_EXPIRATION_TIME_KEY = longPreferencesKey("refreshTokenExpirationTime")
		private const val SIGN_UP_TOKEN_EXPIRATION_TIME = 600000
		private const val ACCESS_TOKEN_EXPIRATION_TIME = 3600000
		private const val REFRESH_TOKEN_EXPIRATION_TIME = 2592000000
	}

	/**
	 * SignUpToken Function
	 */
	override fun getSignUpToken(): Flow<String> {
		return dataStore.data.map { token ->
			token[SIGN_UP_TOKEN_KEY].orEmpty()
		}
	}

	override suspend fun updateSignUpToken(signUpToken: String) {
		dataStore.edit { prefs ->
			prefs[SIGN_UP_TOKEN_KEY] = signUpToken
			prefs[SIGN_UP_TOKEN_EXPIRATION_TIME_KEY] = System.currentTimeMillis() + SIGN_UP_TOKEN_EXPIRATION_TIME
		}
	}

	override suspend fun deleteSignUpToken() {
		dataStore.edit { prefs ->
			prefs.remove(SIGN_UP_TOKEN_KEY)
			prefs.remove(SIGN_UP_TOKEN_EXPIRATION_TIME_KEY)
		}
	}

	/**
	 * AccessToken Function
	 */
	override fun getAccessToken(): Flow<String> {
		return dataStore.data.map { prefs ->
			prefs[ACCESS_TOKEN_KEY].orEmpty()
		}
	}

	override suspend fun updateAccessToken(accessToken: String) {
		dataStore.edit { prefs ->
			prefs[ACCESS_TOKEN_KEY] = accessToken
			prefs[ACCESS_TOKEN_EXPIRATION_TIME_KEY] = System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME
		}
	}

	override suspend fun isAccessTokenExpired(): Boolean {
		val currentTimeMillis = System.currentTimeMillis()
		val accessTokenExpirationTime = getAccessTokenExpirationTime().first()

		return accessTokenExpirationTime != null && currentTimeMillis >= accessTokenExpirationTime
	}

	private fun getAccessTokenExpirationTime(): Flow<Long?> = dataStore.data.map { token ->
		token[ACCESS_TOKEN_EXPIRATION_TIME_KEY]
	}

	/**
	 * RefreshToken Function
	 */
	override fun getRefreshToken(): Flow<String> {
		return dataStore.data.map { prefs ->
			prefs[REFRESH_TOKEN_KEY].orEmpty()
		}
	}

	override suspend fun updateRefreshToken(refreshToken: String) {
		dataStore.edit { prefs ->
			prefs[REFRESH_TOKEN_KEY] = refreshToken
			prefs[REFRESH_TOKEN_EXPIRATION_TIME_KEY] = System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME
		}
	}

	/**
	 * Util Function
	 */
	override suspend fun deleteAllToken() {
		dataStore.edit { token ->
			token.clear()
		}
	}
}