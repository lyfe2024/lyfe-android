package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
	private val repository: NotificationRepository
){
	operator fun invoke() = repository.fetchNotifications()
}