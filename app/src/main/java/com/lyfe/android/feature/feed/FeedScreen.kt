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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.model.TabItem
import com.lyfe.android.core.common.ui.theme.Button1
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey50
import com.lyfe.android.core.common.ui.theme.H3
import com.lyfe.android.core.common.ui.theme.H5
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.feed.image.ImageFeedScreen
import com.lyfe.android.feature.feed.text.TextFeedScreen

private const val LATEST_FEED = 0
private const val POPULAR_FEED = 1

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(
	navigator: LyfeNavigator,
	onScroll: (Boolean) -> Unit
) {
	val tabItemList = listOf(
		TabItem(stringResource(id = R.string.feed_screen_image_tab_text)),
		TabItem(stringResource(id = R.string.feed_screen_text_tab_text))
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
		FeedTopBar(
			tabWidth = width,
			tabItemList = tabItemList,
			selectedTabIndex = pagerState.currentPage,
			tabIdx = tabIdx,
			onChangeTabIdx = { index -> tabIdx = index },
			onNavigateToRequestPhoto = {}
		)

		HorizontalPager(
			modifier = Modifier.fillMaxSize(),
			state = pagerState
		) { page ->
			when (page) {
				LATEST_FEED -> {
					ImageFeedScreen(
						onScroll = onScroll
					) {
						navigator.navigate(LyfeScreens.FeedDetail.name)
					}
				}
				POPULAR_FEED -> {
					TextFeedScreen(
						onScroll = onScroll
					) {
						navigator.navigate(LyfeScreens.FeedDetail.name)
					}
				}
			}
		}
	}
}

@Composable
private fun FeedTopBar(
	tabWidth: Dp,
	tabItemList: List<TabItem>,
	selectedTabIndex: Int,
	tabIdx: Int,
	onChangeTabIdx: (tabIdx: Int) -> Unit,
	onNavigateToRequestPhoto: () -> Unit
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
			style = H3,
			color = Color.Black
		)

		Text(
			modifier = Modifier
				.clickableSingle { onNavigateToRequestPhoto() },
			text = stringResource(id = R.string.feed_screen_request_photo),
			style = Button1,
			color = Main500
		)
	}

	FeedTab(
		modifier = Modifier.width(width = tabWidth),
		tabs = tabItemList,
		selectedTabIndex = selectedTabIndex,
		tabIdx = tabIdx,
		onTabClick = onChangeTabIdx
	)
}

@Composable
private fun FeedTab(
	modifier: Modifier = Modifier,
	tabs: List<TabItem>,
	selectedTabIndex: Int,
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
		selectedTabIndex = selectedTabIndex,
		edgePadding = 0.dp,
		containerColor = Grey50,
		divider = {},
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
						color = getTabTextColor(tabIdx, index),
						style = H5,
						textAlign = TextAlign.Center
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