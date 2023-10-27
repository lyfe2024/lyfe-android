package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.model.Alarm
import kotlinx.coroutines.flow.Flow

interface LyfeRepository {
	fun fetchAlarmList(): Flow<List<Alarm>>
}