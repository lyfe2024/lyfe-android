package com.lyfe.android.core.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userData")

class LocalUserDataSourceImpl @Inject constructor(
	@ApplicationContext private val context: Context
): LocalUserDataSource {

	private object PreferencesKeys {
		val ACCESS_TOKEN = stringPreferencesKey("accessToken")
		val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
	}

	override suspend fun saveUserAccessToken(accessToken: String) {
		context.dataStore.edit { userData ->
			userData[PreferencesKeys.ACCESS_TOKEN] = accessToken
		}
	}

	override suspend fun saveUserRefreshToken(refreshToken: String) {
		context.dataStore.edit { userData ->
			userData[PreferencesKeys.REFRESH_TOKEN] = refreshToken
		}
	}

	override fun getUserAccessToken(): Flow<String> = context.dataStore.data.map { userData ->
		userData[PreferencesKeys.ACCESS_TOKEN].orEmpty()
	}

	override fun getUserRefreshToken(): Flow<String> = context.dataStore.data.map { userData ->
		userData[PreferencesKeys.REFRESH_TOKEN].orEmpty()
	}
}