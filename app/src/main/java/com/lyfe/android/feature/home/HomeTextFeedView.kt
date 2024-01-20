package com.lyfe.android.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.lyfe.android.core.common.ui.theme.TempColor
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.Feed

@Composable
fun HomeTextFeedView(
	modifier: Modifier = Modifier,
	feed: Feed,
	onClick: () -> Unit = {}
) {
	Column(
		modifier = modifier
			.padding(vertical = 8.dp)
			.clickableSingle {
				onClick()
			},
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		HomeTextFeedProfile(feed)

		Text(
			text = feed.title,
			fontSize = 16.sp,
			color = Color.Black,
			fontWeight = FontWeight.W700,
			lineHeight = 24.sp,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis
		)

		Text(
			text = feed.content,
			fontSize = 14.sp,
			color = Color.Black,
			fontWeight = FontWeight.W500,
			overflow = TextOverflow.Ellipsis,
			maxLines = 2
		)

		HomeTextFeedScoreRow(
			whiskyCnt = feed.whiskyCount,
			commentCnt = feed.commentCount
		)
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HomeTextFeedProfile(
	feed: Feed
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		GlideImage(
			modifier = Modifier
				.size(32.dp)
				.clip(CircleShape),
			model = feed.userProfileImgUrl,
			contentDescription = "feed_profile_image",
			contentScale = ContentScale.Crop
		)

		Text(
			text = feed.userName,
			style = TextStyle(
				color = Color.Black,
				fontSize = 14.sp,
				fontWeight = FontWeight.W700,
				fontFamily = pretenard
			),
			overflow = TextOverflow.Ellipsis,
			maxLines = 1
		)

		Text(
			text = feed.date,
			style = TextStyle(
				color = TempColor.CCCCCC,
				fontSize = 10.sp,
				fontWeight = FontWeight.W400,
				fontFamily = pretenard
			)
		)
	}
}

@Composable
private fun HomeTextFeedScoreRow(
	modifier: Modifier = Modifier,
	whiskyCnt: Int,
	commentCnt: Int
) {
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			modifier = Modifier
				.padding(0.5.dp)
				.size(16.dp),
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

		Spacer(modifier = Modifier.width(16.dp))

		Image(
			modifier = Modifier
				.padding(0.66667.dp)
				.size(16.dp),
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

@Preview
@Composable
private fun Preview_HomeTextFeedView() {
	val feed = Feed(
		feedId = 1L,
		title = "여기 텍스트 기반 피드 제목 들어옵니다. ",
		content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
		feedImageUrl = "https://picsum.photos/700/700",
		date = "2021-01-01",
		userId = 2L,
		userName = "유저이름",
		userProfileImgUrl = "https://picsum.photos/700/700",
		whiskyCount = 1,
		commentCount = 1,
		isLike = false
	)

	HomeTextFeedView(
		modifier = Modifier.fillMaxSize(),
		feed = feed,
		onClick = {}
	)
}