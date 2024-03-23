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

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class LocalUserDataSourceImpl @Inject constructor(
	@ApplicationContext private val context: Context
) : LocalUserDataSource {

	private object PreferencesKeys {
		val SOCIAL_TYPE = stringPreferencesKey("socialType")
	}

	override fun getSocialType(): Flow<String> = context.userDataStore.data.map { user ->
		user[PreferencesKeys.SOCIAL_TYPE].orEmpty()
	}

	override suspend fun updateSocialType(socialType: String) {
		context.userDataStore.edit { token ->
			token[PreferencesKeys.SOCIAL_TYPE] = socialType
		}
	}
}