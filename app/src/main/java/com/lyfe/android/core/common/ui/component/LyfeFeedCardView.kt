package com.lyfe.android.core.common.ui.component

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
import com.lyfe.android.core.common.ui.theme.BlackTransparent30
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.model.Feed

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LyfeFeedCardView(
	modifier: Modifier,
	feed: Feed,
	designType: LyfeCardViewDesignType = LyfeCardViewDesignType.HOME_SCREEN_CARD
) {
	Box(
		modifier = modifier
			.widthIn(min = 152.dp)
			.aspectRatio(designType.ratio)
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

		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(BlackTransparent30)
				.padding(designType.contentPadding)
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically
			) {
				GlideImage(
					modifier = Modifier
						.clip(CircleShape)
						.size(designType.userProfileImgSize),
					model = feed.userProfileImgUrl,
					contentDescription = "feed_profile_image",
					contentScale = ContentScale.Crop
				)

				Spacer(modifier = Modifier.width(4.dp))

				Text(
					modifier = Modifier.weight(1f),
					text = feed.userName,
					style = designType.userNameTextStyle,
					overflow = TextOverflow.Ellipsis,
					maxLines = 1
				)
			}

			Column(
				modifier = Modifier
					.align(Alignment.BottomCenter)
			) {
				if (designType == LyfeCardViewDesignType.HOME_SCREEN_CARD) {
					Row(
						modifier = Modifier,
						horizontalArrangement = Arrangement.Start,
						verticalAlignment = Alignment.CenterVertically
					) {
						Image(
							modifier = Modifier
								.size(14.dp),
							painter = painterResource(id = R.drawable.ic_whisky_white),
							contentDescription = "whisky_white_icon"
						)

						Spacer(modifier = Modifier.width(2.dp))

						Text(
							text = feed.whiskyCount.toString(),
							style = TextStyle(
								fontSize = 12.sp,
								lineHeight = 18.sp,
								fontFamily = pretenard,
								fontWeight = FontWeight.W600,
								color = Color.White
							)
						)
					}
				}

				Text(
					modifier = Modifier
						.fillMaxWidth(),
					text = feed.title,
					style = designType.cardContentTextStyle,
					maxLines = 2,
					overflow = TextOverflow.Ellipsis
				)
			}
		}
	}
}

@Preview
@Composable
private fun Preview_LyfeFeedCardView_Home() {
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

	LyfeFeedCardView(
		modifier = Modifier.fillMaxSize(),
		feed = feed,
		designType = LyfeCardViewDesignType.HOME_SCREEN_CARD
	)
}

@Preview
@Composable
private fun Preview_LyfeFeedCardView_Feed() {
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

	LyfeFeedCardView(
		modifier = Modifier.fillMaxSize(),
		feed = feed,
		designType = LyfeCardViewDesignType.FEED_SCREEN_CARD
	)
}