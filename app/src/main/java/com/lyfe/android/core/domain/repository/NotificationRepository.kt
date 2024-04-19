package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.model.Notifications
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
	fun fetchNotifications(
		lastNotiId: Long,
		onStart: () -> Unit,
		onCompletion: () -> Unit,
		onError: (String?) -> Unit
	): Flow<Notifications>
}