package com.lyfe.android.feature.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.domain.usecase.GetAlbumImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SelectAlbumViewModel @Inject constructor(
	private val getAlbumImagesUseCase: GetAlbumImagesUseCase
) : ViewModel() {

	val uiState = getAlbumImagesUseCase()
		.map {
			if (it.isEmpty()) {
				SelectAlbumUiState.EmptyGalleryImages
			} else {
				SelectAlbumUiState.Success(images = it)
			}
		}.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5000L),
			initialValue = SelectAlbumUiState.Loading
		)
}