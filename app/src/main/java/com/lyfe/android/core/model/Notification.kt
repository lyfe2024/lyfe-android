package com.lyfe.android.core.model

data class Notification(
	val notificationType: NotificationType,
	val content: String,
	val notifiedAt: String
)