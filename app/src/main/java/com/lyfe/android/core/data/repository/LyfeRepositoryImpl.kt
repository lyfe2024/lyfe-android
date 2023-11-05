package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.domain.repository.LyfeRepository
import com.lyfe.android.core.model.Alarm
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LyfeRepositoryImpl @Inject constructor(
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : LyfeRepository {
	override fun fetchAlarmList() = flow<List<Alarm>> {
		emit(emptyList())
	}.flowOn(ioDispatcher)
}