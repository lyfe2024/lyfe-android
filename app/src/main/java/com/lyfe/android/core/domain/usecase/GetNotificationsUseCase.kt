package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
	private val repository: NotificationRepository
){
	operator fun invoke(
		lastNotiId: Long,
		onStart: () -> Unit,
		onCompletion: () -> Unit,
		onError: (String?) -> Unit
	) = repository.fetchNotifications(
		lastNotiId = lastNotiId,
		onStart = onStart,
		onCompletion = onCompletion,
		onError = onError
	)
}