package com.lyfe.android.feature.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.lyfe.android.core.common.ui.theme.ScrollTrackColor

@Composable
fun ScrollTrack(
	containerHeightDp: Dp,
	modifier: Modifier = Modifier,
	columnSize: Int,
	gridState: LazyGridState
) {
	val density = LocalDensity.current
	val layoutInfo = remember { derivedStateOf { gridState.layoutInfo } }
	val visibleFirstIndex = remember { derivedStateOf { gridState.firstVisibleItemIndex } }.value
	val scrollOffset = remember { derivedStateOf { gridState.firstVisibleItemScrollOffset } }.value
	val itemCount = layoutInfo.value.totalItemsCount
	val itemHeight = with(density) { layoutInfo.value.visibleItemsInfo.firstOrNull()?.size?.height?.toDp() ?: 0.dp }
	val viewPortHeightDp = with(density) { layoutInfo.value.viewportSize.height.toDp() }
	val scrollOffsetDp = with(density) { scrollOffset.toDp() }
	val cal = if (itemCount < columnSize) 1 else itemCount.divide(columnSize)

	val maxOffset = (itemHeight * cal) - viewPortHeightDp
	val value = itemHeight.times(visibleFirstIndex.divide(columnSize)) + scrollOffsetDp

	var size by remember { mutableStateOf(IntSize.Zero) }

	Box(
		modifier = modifier
			.background(Color.Transparent, shape = RoundedCornerShape(size = 16.dp))
			.onSizeChanged {
				size = it
			}
	) {
		if (0 < size.height && 0 < itemCount) {
			val containerHeight = with(density) { size.height.toDp() }
			val trackHeightRatio = containerHeight / (maxOffset + viewPortHeightDp)
			val offsetByRatio = value * trackHeightRatio
			val trackHeightValue =
				((containerHeightDp.value * containerHeight.value) / (maxOffset.value + viewPortHeightDp.value))
			val trackHeight = trackHeightValue.coerceAtLeast(20f)

			Box(
				Modifier
					.then(
						with(LocalDensity.current) {
							Modifier
								.size(
									width = size.width.toDp(),
									height = trackHeight.dp + offsetByRatio
								)
								.padding(top = offsetByRatio)
						}
					)
					.background(ScrollTrackColor, shape = RoundedCornerShape(size = 16.dp))

			)
		}
	}
}

private fun Int.divide(value: Int): Int {
	if (value == 0 || this < value) return 0

	return if (this % value == 0) {
		this / value
	} else {
		(this / value) + 1
	}
}