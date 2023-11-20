package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GalleryImageResponse

interface DeviceGalleryDataSource {

	suspend fun getAllPhotos(): List<GalleryImageResponse>

	suspend fun getFolderList(): List<String>
}