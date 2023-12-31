package com.lyfe.android.core.common.ui.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
	clickable(
		indication = null,
		interactionSource = remember { MutableInteractionSource() }
	) {
		onClick()
	}
}

fun Modifier.clickableSingle(
	enabled: Boolean = true,
	onClickLabel: String? = null,
	role: Role? = null,
	onClick: () -> Unit
) = composed(
	inspectorInfo = debugInspectorInfo {
		name = "clickable"
		properties["enabled"] = enabled
		properties["onClickLabel"] = onClickLabel
		properties["role"] = role
		properties["onClick"] = onClick
	}
) {
	val multipleEventsCutter = remember { MultipleEventsBlockManager.get() }
	Modifier.clickable(
		enabled = enabled,
		onClickLabel = onClickLabel,
		onClick = { multipleEventsCutter.processEvent { onClick() } },
		role = role,
		indication = null,
		interactionSource = remember { MutableInteractionSource() }
	)
}