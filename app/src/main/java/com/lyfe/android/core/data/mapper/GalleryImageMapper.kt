package com.lyfe.android.core.data.mapper

import com.lyfe.android.core.data.model.GalleryImageResponse
import com.lyfe.android.core.model.GalleryImage

internal fun GalleryImageResponse.toDomain(): GalleryImage =
	GalleryImage(
		id = this.id,
		filepath = this.filepath,
		imageUri = this.uri.toString(),
		name = this.name,
		date = this.date,
		size = this.size
	)