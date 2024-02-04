package com.lyfe.android.core.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class LocalTokenDataSourceImpl @Inject constructor(
	@ApplicationContext private val context: Context
): LocalTokenDataSource {

	private object PreferencesKeys {
		val SIGN_UP_TOKEN = stringPreferencesKey("signUpToken")
		val ACCESS_TOKEN = stringPreferencesKey("accessToken")
		val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
		val SIGN_UP_TOKEN_EXPIRATION_TIME = longPreferencesKey("signUpTokenExpirationTime")
		val ACCESS_TOKEN_EXPIRATION_TIME = longPreferencesKey("accessTokenExpirationTime")
		val REFRESH_TOKEN_EXPIRATION_TIME = longPreferencesKey("refreshTokenExpirationTime")
	}

	// Method to check if the access token has expired
	override suspend fun isAccessTokenExpired(): Boolean {
		val currentTimeMillis = System.currentTimeMillis()
		val accessTokenExpirationTime = getAccessTokenExpirationTime().first()

		return accessTokenExpirationTime != null && currentTimeMillis >= accessTokenExpirationTime
	}

	private fun getAccessTokenExpirationTime(): Flow<Long?> = context.dataStore.data.map { token ->
		token[PreferencesKeys.ACCESS_TOKEN_EXPIRATION_TIME]
	}

	override suspend fun updateSignUpToken(signUpToken: String) {
		context.dataStore.edit { token ->
			token[PreferencesKeys.SIGN_UP_TOKEN] = signUpToken
			token[PreferencesKeys.SIGN_UP_TOKEN_EXPIRATION_TIME] = System.currentTimeMillis() + 600 * 1000
		}
	}

	override suspend fun updateAccessToken(accessToken: String) {
		context.dataStore.edit { token ->
			token[PreferencesKeys.ACCESS_TOKEN] = accessToken
			token[PreferencesKeys.ACCESS_TOKEN_EXPIRATION_TIME] = System.currentTimeMillis() + 3600 * 1000
		}
	}

	override suspend fun updateRefreshToken(refreshToken: String) {
		context.dataStore.edit { token ->
			token[PreferencesKeys.REFRESH_TOKEN] = refreshToken
			token[PreferencesKeys.REFRESH_TOKEN_EXPIRATION_TIME] = System.currentTimeMillis() + 2592000.toLong() * 1000
		}
	}

	override fun getSignUpToken(): Flow<String> = context.dataStore.data.map { token ->
		token[PreferencesKeys.SIGN_UP_TOKEN].orEmpty()
	}

	override fun getAccessToken(): Flow<String> = context.dataStore.data.map { token ->
		token[PreferencesKeys.ACCESS_TOKEN].orEmpty()
	}

	override fun getRefreshToken(): Flow<String> = context.dataStore.data.map { token ->
		token[PreferencesKeys.REFRESH_TOKEN].orEmpty()
	}

	override suspend fun deleteSignUpToken() {
		context.dataStore.edit { token ->
			token.remove(PreferencesKeys.SIGN_UP_TOKEN)
			token.remove(PreferencesKeys.SIGN_UP_TOKEN_EXPIRATION_TIME)
		}
	}

	override suspend fun deleteAllToken() {
		context.dataStore.edit { token ->
			token.clear()
		}
	}
}