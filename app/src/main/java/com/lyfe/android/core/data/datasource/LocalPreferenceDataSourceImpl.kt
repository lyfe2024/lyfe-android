package com.lyfe.android.core.data.datasource

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalPreferenceDataSourceImpl @Inject constructor(
	@ApplicationContext private val context: Context
) : LocalPreferenceDataSource {

	private val localPreferences = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)

	override fun getAccessToken(): String? =
		localPreferences.getString(ACCESS_TOKEN, null)

	override fun updateAccessToken(accessToken: String) {
		localPreferences.edit {
			Log.d("LOCAL_PREFERENCES", "token is updated to $accessToken")
			putString(ACCESS_TOKEN, accessToken)
		}
	}

	override fun removeAccessToken() {
		localPreferences.edit { remove(ACCESS_TOKEN) }
	}

	companion object {
		const val PREFERENCES_NAME = "LYFE"
		const val ACCESS_TOKEN = "ACCESS_TOKEN"
	}
}