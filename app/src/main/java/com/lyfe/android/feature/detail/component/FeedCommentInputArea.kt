package com.lyfe.android.feature.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lyfe.android.core.common.ui.theme.Body2
import com.lyfe.android.core.common.ui.theme.Grey10
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.util.clickableSingle

@Composable
fun FeedCommentInputArea(
	modifier: Modifier = Modifier,
	clickCommentInputArea: () -> Unit = {}
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.clickableSingle { clickCommentInputArea() }
	) {
		Spacer(
			modifier = Modifier
				.fillMaxWidth()
				.height(8.dp)
				.background(Grey10)
		)

		Box(
			modifier = Modifier
				.padding(vertical = 8.dp, horizontal = 12.dp)
		) {
			FeedCommentInputBox()
		}
	}
}

@Composable
private fun FeedCommentInputBox(
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
			.fillMaxWidth()
			.border(width = 1.dp, color = Grey200, shape = RoundedCornerShape(8.dp))
			.padding(vertical = 12.dp, horizontal = 12.dp)
	) {
		Text(
			text = "댓글을 남겨주세요",
			style = Body2,
			color = Grey200
		)
	}
}

@Preview
@Composable
private fun FeedCommentInputAreaPreview() {
	FeedCommentInputArea()
}