package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.NotificationsResponse
import com.lyfe.android.core.data.network.model.Result

interface NotificationDataSource {

	suspend fun fetchNotifications(lastNotiId: Long): Result<NotificationsResponse>
}