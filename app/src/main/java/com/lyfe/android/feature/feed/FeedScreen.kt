package com.lyfe.android.feature.feed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.model.TabItem
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey50
import com.lyfe.android.core.common.ui.theme.Main500

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(
	navigator: LyfeNavigator,
	onScroll: (Boolean) -> Unit
) {
	val tabItemList = listOf(
		TabItem(stringResource(id = R.string.feed_screen_latest_tab_text)),
		TabItem(stringResource(id = R.string.feed_screen_popular_tab_text))
	)
	var tabIdx by remember { mutableIntStateOf(0) }
	val pagerState = rememberPagerState(
		initialPage = 0,
		initialPageOffsetFraction = 0f,
		pageCount = { 2 }
	)

	val density = LocalDensity.current
	var width by remember { mutableStateOf(0.dp) }

	LaunchedEffect(pagerState.currentPage) {
		snapshotFlow { pagerState.currentPage }
			.collect { currentPage ->
				tabIdx = currentPage
				pagerState.animateScrollToPage(currentPage)
			}
	}

	LaunchedEffect(tabIdx) {
		snapshotFlow { tabIdx }
			.collect { currentPage ->
				pagerState.animateScrollToPage(currentPage)
			}
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(Grey50)
			.onGloballyPositioned {
				width = with(density) {
					it.size.width.toDp()
				}
			}
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 20.dp, vertical = 10.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				text = stringResource(id = R.string.feed_screen_title),
				style = TextStyle(
					fontSize = 24.sp,
					lineHeight = 36.sp,
					fontWeight = FontWeight.W700,
					color = Color.Black
				)
			)

			Text(
				modifier = Modifier
					.clickableSingle {
						navigator.navigate(LyfeScreens.Home.name)
					},
				text = stringResource(id = R.string.feed_screen_request_photo),
				style = TextStyle(
					fontSize = 16.sp,
					lineHeight = 24.sp,
					fontWeight = FontWeight.W600,
					color = Main500
				)
			)
		}

		FeedTab(
			modifier = Modifier.width(width = width),
			tabs = tabItemList,
			currentPage = pagerState.currentPage,
			tabIdx = tabIdx,
			onTabClick = { index ->
				tabIdx = index
			}
		)

		HorizontalPager(
			modifier = Modifier.fillMaxSize(),
			state = pagerState
		) { page ->
			when (page) {
				0 -> LatestFeedScreen(
					onScroll = onScroll
				)

				1 -> PopularFeedScreen(
					onScroll = onScroll
				)
			}
		}
	}
}

@Composable
private fun FeedTab(
	modifier: Modifier = Modifier,
	tabs: List<TabItem>,
	currentPage: Int,
	tabIdx: Int,
	onTabClick: (index: Int) -> Unit
) {
	val density = LocalDensity.current
	var tabWidth by remember { mutableStateOf(0.dp) }

	ScrollableTabRow(
		modifier = modifier
			.onGloballyPositioned {
				tabWidth = with(density) {
					it.size.width.toDp() / tabs.size
				}
			},
		selectedTabIndex = currentPage,
		edgePadding = 0.dp,
		containerColor = Grey50,
		indicator = { tabPositions ->
			TabRowDefaults.Indicator(
				modifier = Modifier.tabIndicatorOffset(tabPositions[tabIdx]),
				color = Main500
			)
		}
	) {
		tabs.forEachIndexed { index, item ->
			Tab(
				modifier = Modifier.width(tabWidth),
				text = {
					Text(
						modifier = Modifier,
						text = item.text,
						style = TextStyle(
							fontSize = 18.sp,
							lineHeight = 28.sp,
							fontWeight = FontWeight.W700,
							color = getTabTextColor(tabIdx, index),
							textAlign = TextAlign.Center
						)
					)
				},
				selected = tabIdx == index,
				onClick = { onTabClick(index) }
			)
		}
	}
}

private fun getTabTextColor(
	currentPage: Int,
	tabIdx: Int
) = if (isCurrentTab(currentPage, tabIdx)) {
	Main500
} else {
	Grey200
}

private fun isCurrentTab(currentPage: Int, tabIdx: Int) = currentPage == tabIdx