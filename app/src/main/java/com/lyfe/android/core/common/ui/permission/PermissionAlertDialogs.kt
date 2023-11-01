package com.lyfe.android.core.common.ui.permission

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun PermissionAlertDialogs(
	failedPermissionList: List<NeededPermission>,
	permissionSuccess: (permissionPassedList: List<NeededPermission>) -> Unit = {},
	onDismiss: () -> Unit,
) {
	val permissionPassedList = mutableListOf<NeededPermission>()

	val permissionDialog = remember { mutableStateListOf<NeededPermission>() }
	val activity = LocalContext.current as Activity

	val multiplePermissionLauncher = rememberLauncherForActivityResult(
		ActivityResultContracts.RequestMultiplePermissions()
	) { permissions ->
		permissions.entries.forEach { entry ->
			classifyPermission(
				content = entry.key,
				isSuccess = entry.value,
				onSuccess = { neededPermission ->
					permissionPassedList.add(neededPermission)
				},
			)
		}

		permissionSuccess(permissionPassedList)
	}

	LaunchedEffect(Unit) {
		permissionDialog.addAll(failedPermissionList)
	}

	permissionDialog.forEach { permission ->
		PermissionAlertDialog(
			neededPermission = permission,
			onDismiss = { permissionDialog.remove(permission) },
			onOkClick = {
				permissionDialog.remove(permission)
				multiplePermissionLauncher.launch(arrayOf(permission.permission))
			},
			onGoToAppSettingsClick = {
				permissionDialog.remove(permission)
				activity.goToAppSetting()
			},
			isPermissionDeclined = !activity.shouldShowRequestPermissionRationale(permission.permission)
		)

		if (permissionDialog.size == 0) onDismiss()
	}
}

private fun classifyPermission(
	content: String,
	isSuccess: Boolean,
	onSuccess: (neededPermission: NeededPermission) -> Unit,
) {
	if (isSuccess) onSuccess(getNeededPermission(content))
}

private fun Activity.goToAppSetting() {
	val i = Intent(
		Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
		Uri.fromParts("package", packageName, null)
	)
	startActivity(i)
}