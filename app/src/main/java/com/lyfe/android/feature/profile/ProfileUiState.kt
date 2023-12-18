package com.lyfe.android.feature.profile

import com.lyfe.android.core.model.ImageFeed
import com.lyfe.android.core.model.TextFeed

sealed interface ProfileUiState {

	object GuestSuccess : ProfileUiState

	data class UserSuccess(
		val imageFeeds: List<ImageFeed> = listOf(
			ImageFeed(1), ImageFeed(2), ImageFeed(3), ImageFeed(4), ImageFeed(5), ImageFeed(6)
		),
		val textFeeds: List<TextFeed> = listOf(
			TextFeed(1), TextFeed(2), TextFeed(3), TextFeed(4), TextFeed(5), TextFeed(6),TextFeed(7),
		)
	) : ProfileUiState

	object Loading : ProfileUiState

	object Failure : ProfileUiState
}