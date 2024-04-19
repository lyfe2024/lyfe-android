package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.NotificationsResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.GET

interface NotificationService {

	// 알림 조회
	@GET("https://api.lyfeteam.info/v1/notifications")
	suspend fun fetchNotifications(): Result<NotificationsResponse>
}