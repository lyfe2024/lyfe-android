package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.LyfeRepository
import javax.inject.Inject

class GetAlarmListUseCase @Inject constructor(
	private val repository: LyfeRepository
) {

	operator fun invoke() = repository.fetchAlarmList()
}