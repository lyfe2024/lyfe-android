package com.lyfe.android.core.model

data class Alarm(
	val id: Long,
	val profileImageUrl: String,
	val type: AlarmType,
	val message: String,
	val time: String
)