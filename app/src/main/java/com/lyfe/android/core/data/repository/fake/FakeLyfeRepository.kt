package com.lyfe.android.core.data.repository.fake

import com.lyfe.android.core.domain.repository.LyfeRepository
import com.lyfe.android.core.model.Alarm
import com.lyfe.android.core.model.AlarmType
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeLyfeRepository @Inject constructor() : LyfeRepository {

	override fun fetchAlarmList() = flow {
		val fakeAlarmList = listOf(
			Alarm(
				id = 0L,
				profileImageUrl = "",
				type = AlarmType.REQUEST_PHOTO_REACTION,
				message = "신청하신 사진이 위스키 잔을 받았어요!",
				time = "방금 전"
			),
			Alarm(
				id = 1L,
				profileImageUrl = "",
				type = AlarmType.PHOTO_COMMENTS,
				message = "신청하신 사진에 댓글이 달렸어요. 확인하러 가볼까요?",
				time = "5시간 전"
			),
			Alarm(
				id = 2L,
				profileImageUrl = "",
				type = AlarmType.POST_REACTION,
				message = "작성하신 글이 위스키 잔을 받았어요!",
				time = "하루 전"
			),
			Alarm(
				id = 3L,
				profileImageUrl = "",
				type = AlarmType.POST_COMMENTS,
				message = "작성하신 사진에 댓글이 달렸어요. 확인하러 가볼까요?",
				time = "08.11"
			)
		)
		emit(fakeAlarmList)
	}
}