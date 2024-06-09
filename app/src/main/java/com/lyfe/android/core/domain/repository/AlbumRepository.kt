package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.model.GalleryImage
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AlbumRepository {

	fun getAllPhotos(): Flow<List<GalleryImage>>

	suspend fun translateToFile(localContentUrl: String): File?
}