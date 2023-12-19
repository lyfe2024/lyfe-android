package com.lyfe.android.feature.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.R
import com.lyfe.android.core.model.Feed
import com.lyfe.android.ui.theme.BlackTransparent30
import com.lyfe.android.ui.theme.TempColor

@Composable
fun FeedCardView(
	modifier: Modifier = Modifier,
	feed: Feed
) {
	Column {
		FeedMainBox(
			modifier = modifier,
			feed = feed
		)
		Spacer(modifier = Modifier.height(4.dp))
		FeedScoreRow(
			modifier = Modifier.fillMaxWidth(),
			whiskyCnt = feed.whiskyCount,
			commentCnt = feed.commentCount
		)
	}
}

@Composable
private fun FeedScoreRow(
	modifier: Modifier = Modifier,
	whiskyCnt: Int,
	commentCnt: Int
) {
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_whisky),
			contentDescription = "ic_whisky"
		)

		Spacer(modifier = Modifier.width(2.dp))

		Text(
			text = whiskyCnt.toString(),
			style = TextStyle(
				fontSize = 12.sp,
				lineHeight = 18.sp,
				fontWeight = FontWeight.W600,
				color = TempColor.CCCCCC
			)
		)

		Spacer(modifier = Modifier.width(8.dp))

		Image(
			painter = painterResource(id = R.drawable.ic_comment),
			contentDescription = "ic_whisky"
		)

		Spacer(modifier = Modifier.width(2.dp))

		Text(
			text = commentCnt.toString(),
			style = TextStyle(
				fontSize = 12.sp,
				lineHeight = 18.sp,
				fontWeight = FontWeight.W600,
				color = TempColor.CCCCCC
			)
		)
	}
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun FeedMainBox(
	modifier: Modifier,
	feed: Feed
) {
	Box(
		modifier = modifier
			.widthIn(min = 152.dp)
			.aspectRatio(getMainBoxRatio())
			.fillMaxSize()
			.clip(RoundedCornerShape(16.dp))
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

		Spacer(
			modifier = Modifier
				.fillMaxSize()
				.background(BlackTransparent30)
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
				lineHeight = 22.sp
			),
			overflow = TextOverflow.Ellipsis
		)
	}
}

private fun getMainBoxRatio(): Float {
	val fractionWidth = 152f
	val fractionHeight = 210f
	return fractionWidth / fractionHeight
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