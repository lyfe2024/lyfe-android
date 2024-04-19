package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.NotificationsResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.NotificationService
import javax.inject.Inject

class NotificationRemoteDataSource @Inject constructor(
	private val notificationService: NotificationService
) : NotificationDataSource {
	override suspend fun fetchNotifications(): Result<NotificationsResponse> {
		return notificationService.fetchNotifications()
	}
}