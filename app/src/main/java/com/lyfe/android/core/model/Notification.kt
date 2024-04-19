package com.lyfe.android.core.model

data class Notification(
	val id: Long,
	val targetId: Long,
	val notificationType: NotificationType,
	val content: String,
	val notifiedAt: String
)