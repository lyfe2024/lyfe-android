package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.NotificationsResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationService {

	// 알림 조회
	@GET("/v1/notifications")
	suspend fun fetchNotifications(
		@Query("cursorId") lastNotiId: Long
	): Result<NotificationsResponse>
}