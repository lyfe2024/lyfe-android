package com.lyfe.android.feature.profileedit

import android.graphics.BitmapFactory
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
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

fun File.asRequestBody(contentType: MediaType? = null): RequestBody {
	return object : RequestBody() {
		override fun contentType(): MediaType? = contentType

		override fun writeTo(sink: BufferedSink) {
			source().use { source -> sink.writeAll(source) }
		}
	}
}