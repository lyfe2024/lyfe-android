package com.lyfe.android.core.data.repository

import android.util.Log
import com.lyfe.android.core.data.datasource.NotificationDataSource
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.onException
import com.lyfe.android.core.data.network.model.onFailure
import com.lyfe.android.core.data.network.model.onSuccess
import com.lyfe.android.core.data.network.model.onUnexpected
import com.lyfe.android.core.domain.repository.NotificationRepository
import com.lyfe.android.core.model.Notifications
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Named

class NotificationRepositoryImpl @Inject constructor(
	private val notificationDataSource: NotificationDataSource,
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : NotificationRepository {

	override fun fetchNotifications(
		lastNotiId: Long,
		onStart: () -> Unit,
		onCompletion: () -> Unit,
		onError: (String?) -> Unit
	): Flow<Notifications> = flow {
		Log.e("Test@@@", "Repository fetch")
		notificationDataSource.fetchNotifications(lastNotiId)
			.onSuccess { data ->
				Log.e("Test@@@", "Repository Success data: $data")
				emit(data.toDomain())
			}
			.onFailure { code, error ->
				Log.e("Test@@@", "Repository onFailure ${error}")
				onError(error)
			}
			.onException { exception ->
				Log.e("Test@@@", "Repository onException ${exception.message}")
				onError(exception.message)
			}
			.onUnexpected { throwable ->
				Log.e("Test@@@", "Repository onUnexcepted: ${throwable.message}")
				onError(throwable.message)
			}
	}.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(ioDispatcher)
}