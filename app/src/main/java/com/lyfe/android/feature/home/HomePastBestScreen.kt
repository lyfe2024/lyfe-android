package com.lyfe.android.feature.home

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.core.common.ui.component.LyfeFeedCardView
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun HomePastBestScreen(
	viewModel: HomeViewModel = hiltViewModel(),
	navigator: LyfeNavigator,
	onScroll: (Boolean) -> Unit
) {
	// 백엔드랑 API 어떤식으로 처리할지 의논하고 로직 수정해야할 듯
	val imageFeeds by viewModel.imageFeedList.collectAsStateWithLifecycle()
	val textFeeds by viewModel.textFeedList.collectAsStateWithLifecycle()
	val scrollState = rememberLazyListState()

	LaunchedEffect(Unit) {
		viewModel.fetchImageFeedList()
		viewModel.fetchTextFeedList()
	}

	LaunchedEffect(scrollState) {
		snapshotFlow { scrollState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(16.dp),
		contentPadding = PaddingValues(horizontal = 20.dp),
		state = scrollState
	) {
		items(
			count = 10,
			key = { it }
		) { _ ->
			HomePastBestItem(
				navigator = navigator,
				imageFeeds = imageFeeds,
				textFeeds = textFeeds
			)
		}
	}
}

@Composable
private fun HomePastBestItem(
	navigator: LyfeNavigator,
	imageFeeds: List<Feed>,
	textFeeds: List<Feed>
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.shadow(
				color = Color.Black.copy(alpha = 0.1f),
				offsetY = 8.dp,
				blurRadius = 16.dp
			)
			.background(
				color = Color.White,
				shape = RoundedCornerShape(20.dp)
			)
			.padding(vertical = 16.dp)
	) {
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp),
			textAlign = TextAlign.Start,
			text = "여기에 과거 주제 내용\n" + "문장 들어갑니다.",
			style = TextStyle(
				color = Color.Black,
				fontSize = 20.sp,
				fontWeight = FontWeight.W700,
				lineHeight = 32.sp,
				fontFamily = pretenard
			),
			maxLines = 2,
			overflow = TextOverflow.Ellipsis
		)

		Spacer(modifier = Modifier.height(8.dp))

		HomePastBestFeeds(
			navigator = navigator,
			feeds = imageFeeds
		)

		Spacer(modifier = Modifier.height(8.dp))

		Divider(
			modifier = Modifier.padding(horizontal = 16.dp),
			color = Grey100,
			thickness = 1.dp
		)

		Spacer(modifier = Modifier.height(8.dp))

		HomePastBestFeeds(
			navigator = navigator,
			feeds = textFeeds
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomePastBestFeeds(
	navigator: LyfeNavigator,
	feeds: List<Feed>
) {
	val pages = feeds.subList(0, 3)
	val pagerState = rememberPagerState { pages.size }
	var indicatorIdx by remember { mutableIntStateOf(0) }

	LaunchedEffect(pagerState.currentPage) {
		snapshotFlow { pagerState.currentPage }
			.collect { currentPage ->
				indicatorIdx = currentPage
				pagerState.animateScrollToPage(currentPage)
			}
	}

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		HomePostPastPageIndicator(
			pagerState = pagerState
		)

		Spacer(modifier = Modifier.height(8.dp))

		HomePostPastPager(
			pages = pages,
			pagerState = pagerState,
			onItemClick = {
				navigator.navigate(LyfeScreens.FeedDetail.name)
			}
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomePostPastPageIndicator(
	pagerState: PagerState
) {
	Row(
		Modifier
			.fillMaxWidth()
			.height(10.dp)
			.padding(horizontal = 16.dp),
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		repeat(pagerState.pageCount) { iteration ->
			val modifier = if (pagerState.currentPage == iteration) {
				Modifier
					.weight(1f)
					.height(4.dp)
					.background(color = Main500, shape = RoundedCornerShape(4.dp))
					.align(Alignment.Top)
			} else {
				Modifier
					.weight(1f)
					.height(4.dp)
					.background(color = Grey100, shape = RoundedCornerShape(4.dp))
					.align(Alignment.Bottom)
			}

			Box(modifier = modifier)
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomePostPastPager(
	pages: List<Feed>,
	pagerState: PagerState,
	onItemClick: () -> Unit
) {
	HorizontalPager(
		modifier = Modifier.fillMaxWidth(),
		state = pagerState,
		contentPadding = PaddingValues(horizontal = 16.dp),
		pageSpacing = 16.dp
	) { page ->
		val feed = pages[page]
		if (feed.feedImageUrl.isEmpty()) {
			// 텍스트 피드
			HomeTextFeedView(
				feed = feed,
				onClick = onItemClick
			)
		} else {
			// 이미지 피드
			LyfeFeedCardView(
				modifier = Modifier
					.clickableSingle { onItemClick() },
				feed = feed
			)
		}
	}
}

fun Modifier.shadow(
	color: Color = Color.Black,
	offsetX: Dp = 0.dp,
	offsetY: Dp = 0.dp,
	blurRadius: Dp = 0.dp
) = then(
	drawBehind {
		drawIntoCanvas { canvas ->
			val paint = Paint()
			val frameworkPaint = paint.asFrameworkPaint()
			if (blurRadius != 0.dp) {
				frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
			}
			frameworkPaint.color = color.toArgb()

			val leftPixel = offsetX.toPx()
			val topPixel = offsetY.toPx()
			val rightPixel = size.width + topPixel
			val bottomPixel = size.height + leftPixel

			canvas.drawRect(
				left = leftPixel,
				top = topPixel,
				right = rightPixel,
				bottom = bottomPixel,
				paint = paint
			)
		}
	}
)