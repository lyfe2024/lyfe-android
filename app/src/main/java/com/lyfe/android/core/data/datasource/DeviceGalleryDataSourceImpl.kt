package com.lyfe.android.core.data.datasource

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.os.bundleOf
import com.lyfe.android.core.data.model.GalleryImageResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class DeviceGalleryDataSourceImpl @Inject constructor(
	@ApplicationContext private val context: Context
) : DeviceGalleryDataSource {

	private val uriExternal: Uri by lazy {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
		} else {
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI
		}
	}

	private val projection = arrayOf(
		MediaStore.Images.ImageColumns.DATA,
		MediaStore.Images.ImageColumns.DISPLAY_NAME,
		MediaStore.Images.ImageColumns.DATE_TAKEN,
		MediaStore.Images.ImageColumns._ID
	)

	private val sortedOrder = MediaStore.Images.ImageColumns.DATE_TAKEN

	private val contentResolver by lazy { context.contentResolver }

	override suspend fun getAllPhotos(): List<GalleryImageResponse> {
		val galleryImageList = mutableListOf<GalleryImageResponse>()
		val query = getQuery()

		query?.use { cursor ->
			while (cursor.moveToNext()) {
				val id =
					cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID))
				val name =
					cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME))
				val filepath =
					cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
				val date =
					cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN))
				val contentUri = ContentUris.withAppendedId(uriExternal, id)

				val image = GalleryImageResponse(
					id = id,
					filepath = filepath,
					uri = contentUri,
					name = name,
					date = date ?: "",
					size = 0
				)
				galleryImageList.add(image)
			}
		}
		return galleryImageList
	}

	private fun getQuery(
		selection: String? = null,
		selectionArgs: Array<String>? = null
	) = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
		val bundle = bundleOf(
			ContentResolver.QUERY_ARG_LIMIT to Integer.MAX_VALUE,
			ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_MODIFIED),
			ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
			ContentResolver.QUERY_ARG_SQL_SELECTION to selection,
			ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS to selectionArgs
		)
		contentResolver.query(uriExternal, projection, bundle, null)
	} else {
		contentResolver.query(
			uriExternal,
			projection,
			selection,
			selectionArgs,
			"$sortedOrder DESC"
		)
	}

	override suspend fun getFolderList(): List<String> {
		val folderList = ArrayList<String>()

		val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
		val projection = arrayOf(
			MediaStore.Images.Media.DATA
		)

		val cursor = context.contentResolver.query(uri, projection, null, null, null)
		if (cursor != null) {
			while (cursor.moveToNext()) {
				val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
				val filePath = cursor.getString(columnIndex)
				val folder = File(filePath).parent
				if (folder != null && !folderList.contains(folder)) {
					folderList.add(folder)
				}
			}
			cursor.close()
		}
		return folderList
	}
}