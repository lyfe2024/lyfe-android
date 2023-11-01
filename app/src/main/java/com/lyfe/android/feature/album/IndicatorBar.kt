package com.lyfe.android.feature.album

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun IndicatorBar(
	modifier: Modifier = Modifier,
	listState: LazyGridState,
	columnSize: Int,
) {
	if (!listState.canScrollBackward && !listState.canScrollForward) {
		return
	}

	BoxWithConstraints(modifier = modifier) {

		val heightDp = with(LocalDensity.current) { maxHeight }

		ScrollTrack(
			containerHeightDp = heightDp,
			modifier = Modifier
				.width(6.dp)
				.height(heightDp),
			gridState = listState,
			columnSize = columnSize,
		)
	}
}