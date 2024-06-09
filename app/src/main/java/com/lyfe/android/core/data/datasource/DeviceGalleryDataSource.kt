package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GalleryImageResponse
import java.io.File

interface DeviceGalleryDataSource {

	suspend fun getAllPhotos(): List<GalleryImageResponse>

	suspend fun getFolderList(): List<String>

	suspend fun translateToFile(localContentUrl: String): File?
}