package com.lyfe.android.core.common.ui.util

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FixedThreshold
import androidx.compose.material.ResistanceConfig
import androidx.compose.material.SwipeableDefaults.VelocityThreshold
import androidx.compose.material.SwipeableDefaults.resistanceConfig
import androidx.compose.material.SwipeableState
import androidx.compose.material.ThresholdConfig
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

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

@ExperimentalMaterialApi
fun <T : Any?> Modifier.swipeable(
	state: SwipeableState<T>,
	anchors: Map<Float, T>,
	orientation: Orientation,
	enabled: Boolean = true,
	reverseDirection: Boolean = false,
	interactionSource: MutableInteractionSource? = null,
	thresholds: (from: String, to: String) -> ThresholdConfig = { _, _ -> FixedThreshold(56.dp) },
	resistance: ResistanceConfig? = resistanceConfig(anchors.keys),
	velocityThreshold: Dp = VelocityThreshold
) {
}

suspend fun PointerInputScope.detectDragGestures(
	onDragStart: (Offset) -> Unit = { },
	onDragEnd: () -> Unit = { },
	onDragCancel: () -> Unit = { },
	onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit
) {

}

fun Modifier.draggable(
	state: DraggableState,
	orientation: Orientation,
	enabled: Boolean = true,
	interactionSource: MutableInteractionSource? = null,
	startDragImmediately: Boolean = false,
	onDragStarted: suspend CoroutineScope.(startedPosition: Offset) -> Unit = {},
	onDragStopped: suspend CoroutineScope.(velocity: Float) -> Unit = {},
	reverseDirection: Boolean = false
) {

}