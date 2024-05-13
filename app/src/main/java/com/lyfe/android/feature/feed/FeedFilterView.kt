package com.lyfe.android.feature.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Title3
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.feature.feed.model.FeedSortType


@Composable
fun FeedFilterView(
	modifier: Modifier = Modifier,
	feedSortType: FeedSortType,
	selectSortType: (FeedSortType) -> Unit
) {
	Row(
		modifier = modifier.clickableSingle {
//			selectSortType(feedSortType)
		},
		verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_arrow_down_black),
			contentDescription = "arrow_down"
		)

		Spacer(modifier = Modifier.width(8.dp))

		Text(
			text = feedSortType.content,
			color = Color.Black,
			style = Title3
		)
	}
}