package com.lyfe.android.feature.album

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.domain.usecase.GetAlbumImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectAlbumViewModel @Inject constructor(
	private val getAlbumImagesUseCase: GetAlbumImagesUseCase
) : ViewModel() {

	var uiState by mutableStateOf<SelectAlbumUiState>(SelectAlbumUiState.Loading)
		private set

	init {
		getGalleryPagingImages()
	}

	private fun getGalleryPagingImages() = viewModelScope.launch {
		getAlbumImagesUseCase().collect { images ->
			uiState = SelectAlbumUiState.Success(images = images)
		}
	}
}