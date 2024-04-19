package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.Notifications
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
	fun fetchNotifications(): Flow<Result<Notifications>>
}