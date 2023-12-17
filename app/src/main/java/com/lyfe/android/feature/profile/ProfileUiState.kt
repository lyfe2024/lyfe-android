package com.lyfe.android.feature.profile

import com.lyfe.android.core.model.ImageFeed
import com.lyfe.android.core.model.TextFeed

sealed interface ProfileUiState {

	object GuestSuccess : ProfileUiState

	data class UserSuccess(
		val imageFeeds: List<ImageFeed> = listOf(),
		val textFeeds: List<TextFeed> = listOf(
			TextFeed(
				1, "title 1", "content 1 content 1 content 1 content 1 content 1 content 1", "몇 분 전", 32, 50
			),
			TextFeed(
				2, "title 2", "content 2 content 1 content 1 content 1 content 1 content 1", "몇 분 전", 32, 50
			),
			TextFeed(
				3, "title 3", "content 3 content 1 content 1 content 1 content 1 content 1", "몇 분 전", 32, 50
			),
			TextFeed(
				4, "title 4", "content 4 content 1 content 1 content 1 content 1 content 1", "몇 분 전", 32, 50
			),
			TextFeed(
				5, "title 5", "content 5 content 1 content 1 content 1 content 1 content 1", "몇 분 전", 32, 50
			),
			TextFeed(
				6, "title 6", "content 6 content 1 content 1 content 1 content 1 content 1", "몇 분 전", 32, 50
			)
		)
	) : ProfileUiState

	object Loading : ProfileUiState

	object Failure : ProfileUiState
}