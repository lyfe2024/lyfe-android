package com.lyfe.android.feature.profileedit

import android.graphics.BitmapFactory
import java.io.File

fun File.getImageFormat(): String {
	if (!exists()) {
		throw NoSuchFileException(this, null, "The File is not existed")
	}
	val options = BitmapFactory.Options()
	options.inJustDecodeBounds = true
	BitmapFactory.decodeFile(path, options)
	val mimeType = options.outMimeType
	val index = mimeType.indexOf('/')
	return options.outMimeType.substring(index + 1)
}