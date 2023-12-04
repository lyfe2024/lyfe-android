package com.lyfe.android.feature.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.core.model.Feed

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeedCardView(
	modifier: Modifier = Modifier,
	feed: Feed,
) {
	Box(
		modifier = modifier
			.clip(RoundedCornerShape(16.dp)),
	) {

		GlideImage(
			modifier = Modifier
				.fillMaxSize()
				.clip(RoundedCornerShape(16.dp))
				.align(Alignment.Center),
			model = feed.feedImageUrl,
			contentDescription = "feed_image",
			contentScale = ContentScale.FillBounds
		)

		Row(
			modifier = Modifier.padding(10.dp),
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {
			GlideImage(
				modifier = Modifier
					.clip(CircleShape)
					.size(24.dp),
				model = feed.userProfileImgUrl,
				contentDescription = "feed_profile_image",
				contentScale = ContentScale.Crop
			)

			Spacer(modifier = Modifier.width(4.dp))

			Text(
				modifier = Modifier.weight(1f),
				text = feed.userName,
				style = TextStyle(
					fontSize = 12.sp,
					lineHeight = 18.sp,
					fontWeight = FontWeight.W600,
					color = Color.White
				),
				overflow = TextOverflow.Ellipsis
			)
		}

		Text(
			modifier = Modifier
				.fillMaxWidth()
				.align(Alignment.BottomCenter)
				.padding(10.dp),
			text = feed.title,
			style = TextStyle(
				color = Color.White,
				fontWeight = FontWeight.W700,
				fontSize = 14.sp,
				lineHeight = 22.sp,
			),
			overflow = TextOverflow.Ellipsis
		)
	}
}

@Preview
@Composable
private fun Preview_FeedCardView() {
	val feed = Feed(
		feedId = 1L,
		title = "타이틀1",
		content = "컨텐츠1",
		feedImageUrl = "https://picsum.photos/700/700",
		date = "2021-01-01",
		userId = 2L,
		userName = "홍길동",
		userProfileImgUrl = "https://picsum.photos/700/700",
		whiskyCount = 1,
		commentCount = 1,
		isLike = false
	)

	FeedCardView(
		modifier = Modifier.fillMaxSize(),
		feed = feed
	)
}