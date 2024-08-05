package com.lyfe.android.feature.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.util.clickableSingle

@Composable
fun FeedDetailTopBar(
	modifier: Modifier = Modifier,
	onGloballyPositioned: (LayoutCoordinates) -> Unit = {},
	toggleOptionDialog: () -> Unit,
	navigateUp: () -> Unit
) {
	Row(
		modifier = modifier.fillMaxWidth()
			.onGloballyPositioned(onGloballyPositioned)
			.padding(start = 20.dp, end = 20.dp, top = 16.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			modifier = Modifier
				.size(24.dp)
				.clickableSingle { navigateUp() },
			painter = painterResource(id = R.drawable.ic_arrow_back),
			contentDescription = "ic_arrow_back",
			tint = Color.Black
		)

		Icon(
			modifier = Modifier
				.size(24.dp)
				.clickableSingle { toggleOptionDialog() },
			painter = painterResource(id = R.drawable.ic_quill_meatballs),
			contentDescription = "ic_quill_meatballs",
			tint = Color.Black
		)
	}
}

@Preview
@Composable
private fun FeedDetailTopBarPreview() {
	FeedDetailTopBar(
		toggleOptionDialog = {},
		navigateUp = {}
	)
}