package com.lyfe.android.feature.detail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lyfe.android.core.common.ui.component.LyfeImageView
import com.lyfe.android.core.common.ui.theme.Title1

@Composable
fun FeedDetailImageInfoView(
	modifier: Modifier = Modifier,
	content: String,
	imageUrl: String
) {
	Box(
		modifier = modifier
			.fillMaxWidth()
			.aspectRatio(1f)
	) {
		LyfeImageView(
			modifier = Modifier.fillMaxSize(),
			imageUrl = imageUrl,
			contentDescription = "feed_detail_image_content",
			showBlurOnImage = true
		)

		Text(
			modifier = Modifier
				.fillMaxWidth()
				.align(Alignment.BottomCenter)
				.padding(horizontal = 20.dp, vertical = 16.dp),
			text = content,
			style = Title1,
			color = Color.White
		)
	}
}

@Preview
@Composable
private fun FeedDetailImageInfoViewPreview() {
	FeedDetailImageInfoView(
		content = "사진 제목 텍스트",
		imageUrl = "https://flexible.img.hani.co.kr/flexible/normal/960/960/imgdb/resize/2019/0121/00501111_20190121.JPG"
	)
}