package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.model.GalleryImage
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

	fun getAllPhotos(): Flow<List<GalleryImage>>
}