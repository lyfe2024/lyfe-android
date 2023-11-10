package com.lyfe.android.feature.album

import com.lyfe.android.core.model.GalleryImage

sealed interface SelectAlbumUiState {
	object Loading : SelectAlbumUiState

	data class Success(
		val images: List<GalleryImage>
	) : SelectAlbumUiState

	object EmptyGalleryImages : SelectAlbumUiState

	object Error : SelectAlbumUiState
}