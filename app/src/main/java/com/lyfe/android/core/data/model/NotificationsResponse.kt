package com.lyfe.android.core.data.model

import com.lyfe.android.core.model.Notification
import com.lyfe.android.core.model.Notifications
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationsResponse(
	@SerialName("list") val notificationList: List<NotificationResult>
) {
	fun toDomain() = Notifications(
		notificationList = notificationList.map { it.toDomain() }
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
		notificationType = notificationType,
		content = content,
		notifiedAt = notifiedAt
	)
}