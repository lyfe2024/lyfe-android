package com.lyfe.android.feature.detail

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Grey10
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.core.navigation.navigator.LyfeNavigatorImpl

@Composable
fun FeedDetailScreen(
	modifier: Modifier = Modifier,
	navigator: LyfeNavigator = LyfeNavigatorImpl(),
	viewModel: FeedDetailViewModel = viewModel()
) {
	val scrollState = rememberScrollState()

	LaunchedEffect(Unit) {
		viewModel.fetchCommentList()
	}

	Column(
		modifier = modifier
			.verticalScroll(scrollState)
	) {
		FeedDetailTopBar(
			title = "여기에 오늘의 주제문장이 들어갑니다"
		) {
			navigator.navigateUp()
		}
		ImageFeedDetailInfoView(
			image = "https://media.bunjang.co.kr/product/233392017_1_1692231420_w360.jpg",
			title = "사진 제목 텍스트두줄까지 들어가고 넘어가는건 어떠떨까요떨세셍"
		)
		TextFeedDetailInfoView(
			title = "사진 제목 텍스트두줄까지 들어가고 넘어가는건 어떠떨까요떨세셍",
			content = "국회나 그 위원회의 요구가 있을 때에는 국무총리·국무위원 또는 정부위원은 출석·답변하여야 하며, 국무총리 또는 "
		)
		FeedDetailCommentRow(
			cheersCnt = 40,
			commentCnt = 8,
			profileImg = "",
			userName = "유저이름",
			date = "몇분전"
		)
		Spacer(
			modifier = Modifier
				.fillMaxWidth()
				.height(8.dp)
				.background(Grey10)
		)

		FeedCommentView(
			modifier = Modifier.fillMaxSize(),
			commentList = viewModel.commentList
		)
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun FeedDetailCommentRow(
	modifier: Modifier = Modifier,
	cheersCnt: Int,
	commentCnt: Int,
	profileImg: String,
	userName: String,
	date: String
) {
	Row(
		modifier = modifier
			.padding(horizontal = 20.dp, vertical = 8.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			modifier = Modifier.size(24.dp),
			painter = painterResource(id = R.drawable.ic_glass_cheers),
			contentDescription = "ic_glass_cheers",
			colorFilter = ColorFilter.tint(Color.Black)
		)
		Spacer(modifier = Modifier.width(4.dp))
		Text(
			text = cheersCnt.toString(),
			style = TextStyle(
				fontSize = 14.sp,
				lineHeight = 22.sp,
				fontFamily = pretenard,
				fontWeight = FontWeight.W600,
				color = Color.Black
			)
		)
		Spacer(modifier = Modifier.width(8.dp))
		Image(
			modifier = Modifier.size(24.dp),
			painter = painterResource(id = R.drawable.ic_comment),
			contentDescription = "ic_glass_cheers",
			colorFilter = ColorFilter.tint(Color.Black)
		)
		Spacer(modifier = Modifier.width(4.dp))
		Text(
			text = commentCnt.toString(),
			style = TextStyle(
				fontSize = 14.sp,
				lineHeight = 22.sp,
				fontFamily = pretenard,
				fontWeight = FontWeight.W600,
				color = Color.Black
			)
		)
		Spacer(modifier = Modifier.weight(1f))

		GlideImage(
			modifier = Modifier
				.size(32.dp)
				.clip(CircleShape),
			model = profileImg,
			contentDescription = "profile_img",
			contentScale = ContentScale.Crop
		)
		Spacer(modifier = Modifier.width(8.dp))
		Column {
			Text(
				text = userName,
				style = TextStyle(
					fontSize = 14.sp,
					lineHeight = 22.sp,
					fontFamily = pretenard,
					fontWeight = FontWeight.W700,
					color = Color.Black
				)
			)
			Text(
				text = date,
				style = TextStyle(
					fontSize = 10.sp,
					lineHeight = 16.sp,
					fontFamily = pretenard,
					fontWeight = FontWeight.W400,
					color = Grey300
				)
			)
		}
	}
}

@Composable
private fun TextFeedDetailInfoView(
	modifier: Modifier = Modifier,
	title: String,
	content: String
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = 20.dp)
	) {
		Text(
			text = title,
			style = TextStyle(
				fontSize = 18.sp,
				lineHeight = 28.sp,
				fontFamily = pretenard,
				fontWeight = FontWeight.W700,
				color = Color.Black
			),
			overflow = TextOverflow.Ellipsis,
			maxLines = 2
		)

		Spacer(modifier = Modifier.height(16.dp))

		Text(
			text = content,
			style = TextStyle(
				fontSize = 16.sp,
				lineHeight = 24.sp,
				fontFamily = pretenard,
				fontWeight = FontWeight.W500,
				color = Color.Black
			)
		)
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImageFeedDetailInfoView(
	modifier: Modifier = Modifier,
	image: String,
	title: String
) {
	Box(
		modifier = modifier
			.fillMaxWidth()
			.aspectRatio(1f)
	) {
		GlideImage(
			modifier = Modifier.fillMaxSize(),
			model = image,
			contentDescription = "feed_detail_image_content"
		)

		Text(
			modifier = Modifier
				.fillMaxWidth()
				.align(Alignment.BottomCenter)
				.padding(horizontal = 20.dp, vertical = 16.dp),
			text = title,
			style = TextStyle(
				fontSize = 18.sp,
				lineHeight = 28.sp,
				fontFamily = pretenard,
				fontWeight = FontWeight.W700,
				color = Color.White
			)
		)
	}
}

@Composable
private fun FeedDetailTopBar(
	modifier: Modifier = Modifier,
	title: String,
	navigateUp: () -> Unit
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = 16.dp, horizontal = 20.dp)
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Image(
				modifier = Modifier
					.size(24.dp)
					.clickableSingle {
						navigateUp()
					},
				painter = painterResource(id = R.drawable.ic_arrow_back),
				contentDescription = "ic_arrow_back"
			)

			Image(
				modifier = Modifier
					.size(24.dp)
					.clickableSingle {},
				painter = painterResource(id = R.drawable.ic_quill_meatballs),
				contentDescription = "ic_quill_meatballs"
			)
		}

		Spacer(modifier = Modifier.height(16.dp))

		Text(
			text = title,
			style = TextStyle(
				fontSize = 20.sp,
				lineHeight = 32.sp,
				fontFamily = pretenard,
				fontWeight = FontWeight.W700,
				color = Main500
			)
		)
	}
}

@Preview
@Composable
private fun Preview_FeedDetailScreen() {
	FeedDetailScreen()
}