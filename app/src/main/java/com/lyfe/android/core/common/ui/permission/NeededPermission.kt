package com.lyfe.android.core.common.ui.permission

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.IllegalArgumentException

enum class NeededPermission(
	val permission: String,
	val title: String,
	val description: String,
	val permanentlyDeniedDescription: String
) {
	READ_EXTERNAL_STORAGE(
		permission = android.Manifest.permission.READ_EXTERNAL_STORAGE,
		title = "READ_EXTERNAL_STORAGE",
		description = "READ_EXTERNAL_STORAGE 설정",
		permanentlyDeniedDescription = "READ_EXTERNAL_STORAGE 해줘."
	),

	@RequiresApi(Build.VERSION_CODES.TIRAMISU)
	READ_MEDIA_IMAGES(
		permission = android.Manifest.permission.READ_MEDIA_IMAGES,
		title = "READ_MEDIA_IMAGES",
		description = "READ_MEDIA_IMAGES 설정",
		permanentlyDeniedDescription = "READ_MEDIA_IMAGES 해줘."
	);

	fun permissionTextProvider(isPermanentDenied: Boolean): String {
		return if (isPermanentDenied) permanentlyDeniedDescription else description
	}
}

fun getNeededPermission(permission: String): NeededPermission {
	return NeededPermission.values().find { it.permission == permission }
		?: throw IllegalArgumentException("Permission $permission is not supported")
}