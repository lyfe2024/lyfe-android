package com.lyfe.android.core.common.ui.component

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class LyfeSnackBarVisuals(
	override val actionLabel: String? = null,
	override val duration: SnackbarDuration = SnackbarDuration.Short,
	val iconType: LyfeSnackBarIconType = LyfeSnackBarIconType.SUCCESS,
	override val message: String = "",
	override val withDismissAction: Boolean = false
) : SnackbarVisuals