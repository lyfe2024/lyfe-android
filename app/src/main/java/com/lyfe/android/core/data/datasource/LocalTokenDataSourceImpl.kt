package com.lyfe.android.core.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lyfe.android.core.data.datasource.LocalTokenDataSourceImpl.PreferencesKeys.ACCESS_TOKEN_EXPIRATION_TIME
import com.lyfe.android.core.data.datasource.LocalTokenDataSourceImpl.PreferencesKeys.REFRESH_TOKEN_EXPIRATION_TIME
import com.lyfe.android.core.data.datasource.LocalTokenDataSourceImpl.PreferencesKeys.SIGN_UP_TOKEN_EXPIRATION_TIME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class LocalTokenDataSourceImpl @Inject constructor(
	@ApplicationContext private val context: Context
) : LocalTokenDataSource {

	private object PreferencesKeys {
		val SIGN_UP_TOKEN_KEY = stringPreferencesKey("signUpToken")
		val ACCESS_TOKEN_KEY = stringPreferencesKey("accessToken")
		val REFRESH_TOKEN_KEY = stringPreferencesKey("refreshToken")
		val SIGN_UP_TOKEN_EXPIRATION_TIME_KEY = longPreferencesKey("signUpTokenExpirationTime")
		val ACCESS_TOKEN_EXPIRATION_TIME_KEY = longPreferencesKey("accessTokenExpirationTime")
		val REFRESH_TOKEN_EXPIRATION_TIME_KEY = longPreferencesKey("refreshTokenExpirationTime")
		const val SIGN_UP_TOKEN_EXPIRATION_TIME = 600000
		const val ACCESS_TOKEN_EXPIRATION_TIME = 3600000
		const val REFRESH_TOKEN_EXPIRATION_TIME = 2592000000
	}

	// Method to check if the access token has expired
	override suspend fun isAccessTokenExpired(): Boolean {
		val currentTimeMillis = System.currentTimeMillis()
		val accessTokenExpirationTime = getAccessTokenExpirationTime().first()

		return accessTokenExpirationTime != null && currentTimeMillis >= accessTokenExpirationTime
	}

	private fun getAccessTokenExpirationTime(): Flow<Long?> = context.tokenDataStore.data.map { token ->
		token[PreferencesKeys.ACCESS_TOKEN_EXPIRATION_TIME_KEY]
	}

	override suspend fun updateSignUpToken(signUpToken: String) {
		context.tokenDataStore.edit { token ->
			token[PreferencesKeys.SIGN_UP_TOKEN_KEY] = signUpToken
			token[PreferencesKeys.SIGN_UP_TOKEN_EXPIRATION_TIME_KEY] = System.currentTimeMillis() + SIGN_UP_TOKEN_EXPIRATION_TIME
		}
	}

	override suspend fun updateAccessToken(accessToken: String) {
		context.tokenDataStore.edit { token ->
			token[PreferencesKeys.ACCESS_TOKEN_KEY] = accessToken
			token[PreferencesKeys.ACCESS_TOKEN_EXPIRATION_TIME_KEY] = System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME
		}
	}

	override suspend fun updateRefreshToken(refreshToken: String) {
		context.tokenDataStore.edit { token ->
			token[PreferencesKeys.REFRESH_TOKEN_KEY] = refreshToken
			token[PreferencesKeys.REFRESH_TOKEN_EXPIRATION_TIME_KEY] = System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME
		}
	}

	override fun getSignUpToken(): Flow<String> = context.tokenDataStore.data.map { token ->
		token[PreferencesKeys.SIGN_UP_TOKEN_KEY].orEmpty()
	}

	override fun getAccessToken(): Flow<String> = context.tokenDataStore.data.map { token ->
		token[PreferencesKeys.ACCESS_TOKEN_KEY].orEmpty()
	}

	override fun getRefreshToken(): Flow<String> = context.tokenDataStore.data.map { token ->
		token[PreferencesKeys.REFRESH_TOKEN_KEY].orEmpty()
	}

	override suspend fun deleteSignUpToken() {
		context.tokenDataStore.edit { token ->
			token.remove(PreferencesKeys.SIGN_UP_TOKEN_KEY)
			token.remove(PreferencesKeys.SIGN_UP_TOKEN_EXPIRATION_TIME_KEY)
		}
	}

	override suspend fun deleteAllToken() {
		context.tokenDataStore.edit { token ->
			token.clear()
		}
	}
}