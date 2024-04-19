package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.NotificationDataSource
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.NotificationRepository
import com.lyfe.android.core.model.Notification
import com.lyfe.android.core.model.Notifications
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
	private val notificationDataSource: NotificationDataSource
) : NotificationRepository {

	override fun fetchNotifications(): Flow<Result<Notifications>> = flow {
		val result: Result<Notifications> = when (
			val response = notificationDataSource.fetchNotifications()
		) {
			is Result.Success -> {
				Result.Success(response.body?.toDomain())
			}

			is Result.Failure -> {
				Result.Failure(code = response.code, error = response.error)
			}

			is Result.NetworkError -> {
				Result.NetworkError(exception = response.exception)
			}

			is Result.Unexpected -> {
				Result.Unexpected(t = response.t)
			}
		}

		emit(result)
	}
}