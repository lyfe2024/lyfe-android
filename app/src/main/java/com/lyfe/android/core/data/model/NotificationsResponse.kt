package com.lyfe.android.core.data.model

import com.lyfe.android.core.model.Notification
import com.lyfe.android.core.model.NotificationType
import com.lyfe.android.core.model.Notifications
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationsResponse(
	val notificationList: List<NotificationResult>,
	val page: PageResult
) {
	fun toDomain() = Notifications(
		notificationList = notificationList.map { it.toDomain() },
		page = page.toDomain()
	)
}

@Serializable
data class NotificationResult(
	val id: Long,
	@SerialName("notificationTargetId") val targetId: Long,
	val notificationType: String,
	val content: String,
	val notifiedAt: String
) {
	fun toDomain() = Notification(
		id = id,
		targetId = targetId,
		notificationType = NotificationType.findByValue(notificationType),
		content = content,
		notifiedAt = notifiedAt
	)
}