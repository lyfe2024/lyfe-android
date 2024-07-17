package com.lyfe.android.feature.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Body3
import com.lyfe.android.core.common.ui.theme.Button2
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.Grey600
import com.lyfe.android.core.common.ui.theme.Grey900
import com.lyfe.android.core.common.ui.theme.Title3
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.feature.feed.model.FeedSortType

@Composable
fun FeedFilterView(
	modifier: Modifier = Modifier,
	feedSortType: FeedSortType,
	onClick: () -> Unit
) {
	Row(
		modifier = modifier
			.clickableSingle {
				onClick()
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

@Composable
fun SelectFilterListView(
	modifier: Modifier = Modifier,
	feedSortType: FeedSortType,
	onSelectSortType: (FeedSortType) -> Unit
) {
	Column(
		modifier = modifier
			.width(132.dp)
			.background(
				color = Color.White,
				shape = RoundedCornerShape(6.dp)
			)
	) {
		SelectFilterView(
			isSelected = feedSortType == FeedSortType.LATEST,
			feedSortType = FeedSortType.LATEST,
			onSelect = onSelectSortType
		)

		Divider(
			color = Grey100,
			thickness = 1.dp
		)

		SelectFilterView(
			isSelected = feedSortType == FeedSortType.WHISKY,
			feedSortType = FeedSortType.WHISKY,
			onSelect = onSelectSortType
		)

		Divider(
			color = Grey100,
			thickness = 1.dp
		)

		SelectFilterView(
			isSelected = feedSortType == FeedSortType.COMMENT,
			feedSortType = FeedSortType.COMMENT,
			onSelect = onSelectSortType
		)
	}
}

@Composable
fun SelectFilterView(
	isSelected: Boolean,
	feedSortType: FeedSortType,
	onSelect: (FeedSortType) -> Unit
) {
	val alpha = if (isSelected) 1f else 0f

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 4.dp)
			.clickableSingle { onSelect(feedSortType) }
	) {
		Image(
			modifier = Modifier.padding(horizontal = 8.dp)
				.alpha(alpha),
			painter = painterResource(id = R.drawable.ic_check_red),
			contentDescription = "현재 필터링 표시"
		)

		Text(
			modifier = Modifier.padding(end = 8.dp),
			text = feedSortType.content,
			color = if (isSelected) Grey900 else Grey600,
			style = if (isSelected) Button2 else Body3
		)
	}
}