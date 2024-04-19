package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.NotificationResult
import com.lyfe.android.core.data.model.NotificationsResponse
import com.lyfe.android.core.data.model.PageResult
import com.lyfe.android.core.data.network.model.Result
import javax.inject.Inject

class NotificationFakeDataSource @Inject constructor(

) : NotificationDataSource {

	override suspend fun fetchNotifications(lastNotiId: Long): Result<NotificationsResponse> {
		return Result.Success(
			NotificationsResponse(
				notificationList = listOf(
					NotificationResult(
						id = 1L,
						targetId = 1L,
						notificationType = "as",
						content = "aaa",
						notifiedAt = "12313"
					),
					NotificationResult(
						id = 2L,
						targetId = 2L,
						notificationType = "as",
						content = "aaa",
						notifiedAt = "12313"
					),
					NotificationResult(
						id = 3L,
						targetId = 3L,
						notificationType = "as",
						content = "aaa",
						notifiedAt = "12313"
					),
					NotificationResult(
						id = 4L,
						targetId = 4L,
						notificationType = "as",
						content = "aaa",
						notifiedAt = "12313"
					),
					NotificationResult(
						id = 4L,
						targetId = 4L,
						notificationType = "as",
						content = "aaa",
						notifiedAt = "12313"
					),
					NotificationResult(
						id = 5L,
						targetId = 5L,
						notificationType = "as",
						content = "aaa",
						notifiedAt = "12313"
					),
					NotificationResult(
						id = 6L,
						targetId = 6L,
						notificationType = "as",
						content = "aaa",
						notifiedAt = "12313"
					),
					NotificationResult(
						id = 7L,
						targetId = 7L,
						notificationType = "as",
						content = "aaa",
						notifiedAt = "12313"
					),
					NotificationResult(
						id = 8L,
						targetId = 8L,
						notificationType = "as",
						content = "aaa",
						notifiedAt = "12313"
					),
					NotificationResult(
						id = 9L,
						targetId = 9L,
						notificationType = "as",
						content = "aaa",
						notifiedAt = "12313"
					)
				),
				page = PageResult(size = 10, number = 1, totalElements = 1, totalPages = 1)
			)
		)
	}
}