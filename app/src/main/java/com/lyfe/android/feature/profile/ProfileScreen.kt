package com.lyfe.android.feature.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.theme.Grey500
import com.lyfe.android.core.common.ui.theme.Grey900
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.model.ImageFeed
import com.lyfe.android.core.model.TextFeed
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
	viewModel: ProfileViewModel = hiltViewModel(),
	navigator: LyfeNavigator
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(top = 16.dp, bottom = 0.dp)
	) {
		ClickableText(
			modifier = Modifier
				.align(Alignment.End)
				.padding(horizontal = 20.dp),
			text = AnnotatedString("설정"),
			style = TextStyle(
				fontSize = 16.sp,
				fontWeight = FontWeight.W600,
				color = Color.Black
			),
			onClick = { navigator.navigate(LyfeScreens.Setting.route) }
		)

		Spacer(modifier = Modifier.height(16.dp))

		ProfileContentArea(viewModel, navigator)
	}
}

@Composable
private fun ProfileContentArea(
	viewModel: ProfileViewModel,
	navigator: LyfeNavigator
) {
	when (viewModel.uiState) {
		is ProfileUiState.GuestSuccess,
		is ProfileUiState.UserSuccess -> {
			val isGuest = viewModel.uiState is ProfileUiState.GuestSuccess

			ProfileUserInfo(
				navigator = navigator,
				isGuest = isGuest,
				profileImage = null
			)

			Spacer(modifier = Modifier.height(16.dp))

			ProfileUserPostPager(
				viewModel = viewModel,
				isGuest = isGuest,
				navigator = navigator
			)
		}
		is ProfileUiState.Failure -> {
			// 로딩 실패
		}
		is ProfileUiState.Loading -> {
			// 로딩 중 표시
		}
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProfileUserInfo(
	navigator: LyfeNavigator,
	isGuest: Boolean,
	profileImage: String?,
	nickname: String = "익명의 쿼카"
) {
	Row (
		modifier = Modifier
			.height(48.dp)
			.padding(horizontal = 20.dp)
	){
		GlideImage(
			modifier = Modifier
				.fillMaxHeight()
				.aspectRatio(1.0f),
			model = profileImage ?: R.drawable.ic_profile_default,
			failure = placeholder(R.drawable.ic_profile_default),
			contentDescription = "프로필 이미지"
		)

		Spacer(modifier = Modifier.width(16.dp))

		Column(
			modifier = Modifier
				.fillMaxHeight(),
			verticalArrangement = Arrangement.Center
		) {
			Text(
				text = nickname,
				fontSize = 20.sp,
				fontWeight = FontWeight.W700,
				lineHeight = 32.sp,
				color = Color.Black,
			)

			if (!isGuest) {
				ClickableText(
					text = AnnotatedString("프로필 수정"),
					style = TextStyle(
						fontSize = 12.sp,
						color = Grey300
					),
					onClick = { navigator.navigate(LyfeScreens.ProfileEdit.route) }
				)
			}
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileUserPostPager(
	viewModel: ProfileViewModel,
	isGuest: Boolean = true,
	navigator: LyfeNavigator
) {
	val pages = listOf("신청 사진", "고민 글")
	val pagerState = rememberPagerState { pages.size }

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		TabRow(
			selectedTabIndex = pagerState.currentPage,
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 20.dp),
			containerColor = Color.Transparent,
			indicator = {
				TabRowDefaults.Indicator(
					modifier = Modifier.tabIndicatorOffset(it[pagerState.currentPage]),
					color = Main500
				)
			},
			divider = { }
		) {
			pages.forEachIndexed { index, title ->
				ProfileTab(pagerState = pagerState, index = index, title = title)
			}
		}

		if (isGuest) {
			// 게스트는 로그인 유도창 띄우기
			ProfileGuestLoginView(viewModel = viewModel, navigator = navigator)
		}

		Spacer(modifier = Modifier.height(16.dp))

		ProfileUserPostPager(
			navigator = navigator,
			viewModel = viewModel,
			isGuest = isGuest,
			pagerState = pagerState
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileTab(
	pagerState: PagerState,
	index: Int,
	title: String,
) {
	val coroutineScope = rememberCoroutineScope()

	Tab(
		text = {
			Text(
				text = title,
				fontSize = 16.sp,
				color = if (pagerState.currentPage == index) Main500 else Grey200
			)
		},
		selected = pagerState.currentPage == index,
		onClick = {
			coroutineScope.launch {
				pagerState.scrollToPage(index)
			}
		}
	)
}

@Composable
private fun ProfileGuestLoginView(
	viewModel: ProfileViewModel,
	navigator: LyfeNavigator
) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = CenterHorizontally
	) {
		Spacer(modifier = Modifier.fillMaxHeight(0.2f))

		Text(
			text = "로그인 하시면 신청한 사진,\n작성한 고민글을 모아 볼 수 있어요",
			fontSize = 18.sp,
			fontWeight = FontWeight.W700,
			lineHeight = 28.sp,
			color = Grey900,
			textAlign = TextAlign.Center
		)

		Spacer(modifier = Modifier.height(40.dp))

		LyfeButton(
			modifier = Modifier.align(CenterHorizontally),
			isClearIconShow = false,
			text = "로그인 하러 가기",
			buttonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT,
			verticalPadding = 12.dp,
			horizontalPadding = 24.dp
		) {
			// TODO 로그인 화면으로
//			navigator.navigate(route = LyfeScreens.Login.route)
			viewModel.updateUiState(ProfileUiState.UserSuccess())
		}

		Spacer(modifier = Modifier.height(8.dp))

		ClickableText(
			modifier = Modifier.align(CenterHorizontally),
			text = AnnotatedString("아직 회원이 아니신가요?"),
			style = TextStyle(
				fontSize = 14.sp,
				fontWeight = FontWeight.W400,
				color = Grey500,
				textAlign = TextAlign.Center
			)
		) {
			// TODO 로그인 화면으로
//			navigator.navigate(route = LyfeScreens.Login.route)
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileUserPostPager(
	navigator: LyfeNavigator,
	viewModel: ProfileViewModel,
	isGuest: Boolean,
	pagerState: PagerState
) {
	HorizontalPager(
		modifier = Modifier.fillMaxSize(),
		state = pagerState
	) { page ->
		if (isGuest) return@HorizontalPager

		val imageFeeds = (viewModel.uiState as ProfileUiState.UserSuccess).imageFeeds
		val textFeeds = (viewModel.uiState as ProfileUiState.UserSuccess).textFeeds
		when (page) {
			0 -> {
				// 신청 사진 리스트
				ProfileUserImageFeedContent(
					navigator = navigator,
					viewModel = viewModel,
					imageFeeds = imageFeeds
				)
			}
			1 -> {
				// 고민 글
				ProfileUserTextFeedContent(
					navigator = navigator,
					viewModel = viewModel,
					textFeeds = textFeeds
				)
			}
		}
	}
}

@Composable
private fun ProfileUserImageFeedContent(
	navigator: LyfeNavigator,
	viewModel: ProfileViewModel,
	imageFeeds: List<ImageFeed>
) {
	LazyVerticalGrid(
		columns = GridCells.Fixed(2),
		contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 16.dp),
		horizontalArrangement = Arrangement.spacedBy(16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		items(imageFeeds.size) { index ->
			LyfeImageFeed(
				modifier = Modifier,
				paddingValues = PaddingValues(all = 10.dp),
				imageFeed = imageFeeds[index],
				onClick = { }
			)
		}
	}
}

@Composable
private fun ProfileUserTextFeedContent(
	navigator: LyfeNavigator,
	viewModel: ProfileViewModel,
	textFeeds: List<TextFeed>
) {
	LazyColumn(
		contentPadding = PaddingValues(horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		items(textFeeds, key = { it.id }) { data ->
			LyfeTextFeed(
				textFeed = data,
				onClick = { }
			)

			Spacer(modifier = Modifier.height(12.dp))

			Divider(color = Grey100, thickness = 1.dp)
		}
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LyfeImageFeed(
	modifier: Modifier = Modifier,
	paddingValues: PaddingValues,
	imageFeed: ImageFeed,
	onClick: () -> Unit
) {
	Column(
		modifier = modifier
			.fillMaxWidth(),
		horizontalAlignment = Alignment.Start
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.clip(shape = RoundedCornerShape(16.dp))
				.aspectRatio(0.7f)
				.clickable { onClick() }
		) {
			GlideImage(
				modifier = Modifier.fillMaxSize(),
				contentScale = ContentScale.FillBounds,
				model = "https://picsum.photos/300/360",
				contentDescription = "이미지 피드"
			)

			Column(modifier = Modifier.padding(paddingValues)) {
				Row(verticalAlignment = Alignment.CenterVertically) {
					Image(
						modifier = Modifier
							.size(24.dp)
							.clip(CircleShape),
						painter = painterResource(id = R.drawable.ic_profile_default),
						contentDescription = "유저 프로필 썸네일"
					)

					Spacer(modifier = Modifier.width(4.dp))
					
					Text(
						text = imageFeed.nickname,
						color = Color.White,
						fontSize = 12.sp,
						fontWeight = FontWeight.W600
					)
				}

				Spacer(modifier = Modifier.weight(1f))

				Text(
					text = imageFeed.title,
					color = Color.White,
					fontSize = 14.sp,
					fontWeight = FontWeight.W700,
					maxLines = 2
				)
			}
		}

		Spacer(modifier = Modifier.height(4.dp))

		Row(verticalAlignment = Alignment.CenterVertically) {
			Image(
				painter = painterResource(id = R.drawable.ic_drink_wine_regular),
				contentDescription = "리액션 아이콘"
			)

			Text(
				text = imageFeed.reactionCount.toString(),
				fontSize = 12.sp,
				fontWeight = FontWeight.W400,
				color = Grey300
			)

			Spacer(modifier = Modifier.width(8.dp))

			Image(
				painter = painterResource(id = R.drawable.ic_comment),
				contentDescription = "댓글 아이콘"
			)

			Text(
				text = imageFeed.commentCount.toString(),
				fontSize = 12.sp,
				fontWeight = FontWeight.W400,
				color = Grey300
			)
		}
	}
}

@Composable
fun LyfeTextFeed(
	modifier: Modifier = Modifier,
	textFeed: TextFeed,
	onClick: () -> Unit
) {
	Column(
		modifier = modifier.clickable { onClick() },
		horizontalAlignment = Alignment.Start
	) {
		Text(
			text = textFeed.createdAt,
			fontSize = 10.sp,
			color = Grey300,
			fontWeight = FontWeight.W400
		)

		Text(
			text = textFeed.title,
			fontSize = 16.sp,
			color = Color.Black,
			fontWeight = FontWeight.W700,
			lineHeight = 24.sp
		)

		Text(
			text = textFeed.content,
			fontSize = 14.sp,
			color = Color.Black,
			fontWeight = FontWeight.W500,
			maxLines = 2
		)

		Spacer(modifier = Modifier.height(4.dp))

		Row(verticalAlignment = Alignment.CenterVertically) {
			Image(
				painter = painterResource(id = R.drawable.ic_drink_wine_regular),
				contentDescription = "리액션 아이콘"
			)

			Text(
				text = textFeed.reactionCount.toString(),
				fontSize = 12.sp,
				fontWeight = FontWeight.W400,
				color = Grey300
			)

			Spacer(modifier = Modifier.width(16.dp))

			Image(
				painter = painterResource(id = R.drawable.ic_comment),
				contentDescription = "댓글 아이콘"
			)

			Text(
				text = textFeed.commentCount.toString(),
				fontSize = 12.sp,
				fontWeight = FontWeight.W400,
				color = Grey300
			)
		}
	}
}