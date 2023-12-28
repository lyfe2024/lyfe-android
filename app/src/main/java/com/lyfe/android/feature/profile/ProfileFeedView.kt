package com.lyfe.android.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeCardViewDesignType
import com.lyfe.android.core.common.ui.component.LyfeFeedCardView
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.theme.TempColor
import com.lyfe.android.core.model.Feed

@Composable
fun ProfileScreenImageFeedView(
	modifier: Modifier = Modifier,
	feed: Feed
) {
	Column(
		modifier = modifier
	) {
		LyfeFeedCardView(
			modifier = Modifier.wrapContentSize(),
			feed = feed,
			designType = LyfeCardViewDesignType.PROFILE_SCREEN_CARD
		)
		Spacer(modifier = Modifier.height(4.dp))
		ProfileFeedScoreRow(
			modifier = Modifier.fillMaxWidth(),
			whiskyCnt = feed.whiskyCount,
			commentCnt = feed.commentCount
		)
	}
}

@Composable
fun ProfileScreenTextFeedView(
	modifier: Modifier,
	feed: TextFeed
) {
	Column(
		modifier = modifier
	) {
		ProfileTextFeed(
			modifier = Modifier.wrapContentSize(),
			feed = feed
		)

		Spacer(modifier = Modifier.height(4.dp))

		ProfileFeedScoreRow(
			modifier = Modifier.fillMaxWidth(),
			whiskyCnt = feed.whiskyCount,
			commentCnt = feed.commentCount
		)
	}
}

@Composable
fun ProfileTextFeed(
	modifier: Modifier = Modifier,
	feed: TextFeed
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.Start
	) {
		androidx.compose.material3.Text(
			text = feed.date,
			fontSize = 10.sp,
			color = Grey300,
			fontWeight = FontWeight.W400,
			lineHeight = 16.sp
		)

		Spacer(modifier = Modifier.height(4.dp))

		androidx.compose.material3.Text(
			text = feed.title,
			fontSize = 16.sp,
			color = Color.Black,
			fontWeight = FontWeight.W700,
			lineHeight = 24.sp
		)

		Spacer(modifier = Modifier.height(4.dp))

		androidx.compose.material3.Text(
			text = feed.content,
			fontSize = 14.sp,
			color = Color.Black,
			fontWeight = FontWeight.W500,
			maxLines = 2
		)
	}
}

@Composable
private fun ProfileFeedScoreRow(
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
private fun Preview_ProfileFeedView() {
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
	val textFeed = TextFeed(
		feedId = 1L,
		title = "타이틀1",
		content = "텍스트 피드 컨텐츠 내용 텍스트 피드 컨텐츠 내용 텍스트 피드 컨텐츠 내용 텍스트 피드 컨텐츠 내용 ",
		date = "2021-01-01",
		userId = 2L,
		userName = "홍길동",
		whiskyCount = 23,
		commentCount = 50,
		isLike = false
	)

	ProfileScreenImageFeedView(
		modifier = Modifier.fillMaxSize(),
		feed = feed
	)

	ProfileScreenTextFeedView(
		modifier = Modifier.fillMaxWidth(),
		feed = textFeed
	)
}