package com.lyfe.android.core.common.ui.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun PermissionsCheckScreen(
	neededPermissions: Array<String>,
	onPermissionCheckResult: (
		permissionPassedList: List<NeededPermission>,
		permissionFailedList: List<NeededPermission>
	) -> Unit
) {
	val permissionPassedList = mutableListOf<NeededPermission>()
	val permissionFailedList = mutableListOf<NeededPermission>()

	val multiplePermissionLauncher = rememberLauncherForActivityResult(
		ActivityResultContracts.RequestMultiplePermissions()
	) { permissions ->
		permissions.entries.forEach { entry ->
			val neededPermission = getNeededPermission(entry.key)

			if (entry.value) {
				permissionPassedList.add(neededPermission)
			} else {
				permissionFailedList.add(neededPermission)
			}
		}
		onPermissionCheckResult(permissionPassedList, permissionFailedList)
	}

	LaunchedEffect(Unit) {
		multiplePermissionLauncher.launch(neededPermissions)
	}
}