package com.lyfe.android.feature.feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.lyfe.android.core.common.ui.model.TabItem
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.ui.theme.Grey200
import com.lyfe.android.ui.theme.Main500

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScreen(
	navigator: LyfeNavigator,
) {
	val tabItemList = listOf(TabItem("최신"), TabItem("인기"))
	var tabIdx by remember { mutableIntStateOf(0) }
	val pagerState = rememberPagerState()

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
			.onGloballyPositioned {
				width = with(density) {
					it.size.width.toDp()
				}
			}
	) {
		Row {
			Text(
				modifier = Modifier,
				text = "Feed"
			)

			Button(
				onClick = {
					navigator.navigate(LyfeScreens.Home.name)
				}
			) {}
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
			modifier = Modifier
				.fillMaxSize()
				.background(Color.Cyan),
			count = tabItemList.size,
			state = pagerState,
		) { page ->
			when (page) {
				0 -> LatestFeedScreen()
				1 -> PopularFeedScreen()
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
	onTabClick: (index: Int) -> Unit,
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
				onClick = { onTabClick(index) },
			)
		}
	}
}

private fun getTabTextColor(
	currentPage: Int,
	tabIdx: Int,
) = if (isCurrentTab(currentPage, tabIdx)) {
	Main500
} else {
	Grey200
}

private fun isCurrentTab(currentPage: Int, tabIdx: Int) = currentPage == tabIdx