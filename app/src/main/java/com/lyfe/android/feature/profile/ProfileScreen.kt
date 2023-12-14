package com.lyfe.android.feature.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
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
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.ui.theme.Grey200
import com.lyfe.android.ui.theme.Grey500
import com.lyfe.android.ui.theme.Grey900
import com.lyfe.android.ui.theme.Main500
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
	viewModel: ProfileViewModel = hiltViewModel(),
	navigator: LyfeNavigator
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 0.dp)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight()
				.padding(start = 0.dp, end = 0.dp, top = 16.dp, bottom = 16.dp)
		) {
			IconButton(
				modifier = Modifier
					.align(Alignment.CenterVertically)
					.size(24.dp),
				onClick = { navigator.navigateUp() }
			) {
				Image(
					modifier = Modifier.fillMaxSize(),
					painter = painterResource(id = R.drawable.ic_arror_back_black),
					contentDescription = "뒤로 가기 버튼"
				)
			}

			Spacer(modifier = Modifier.weight(1f))

			ClickableText(
				modifier = Modifier.align(Alignment.CenterVertically),
				text = AnnotatedString("설정"),
				style = TextStyle(
					fontSize = 16.sp,
					fontWeight = FontWeight.W600,
					color = Color.Black
				),
				onClick = {
					// TODO 설정 창으로 이동
				}
			)
		}

		Spacer(modifier = Modifier.height(21.dp))

		ProfileContentArea(viewModel, navigator)
	}
}

@Composable
private fun ProfileContentArea(
	viewModel: ProfileViewModel,
	navigator: LyfeNavigator
) {
	when (viewModel.uiState) {
		is ProfileUiState.Success -> {
			val uiState = viewModel.uiState as ProfileUiState.Success
			if (uiState.isGuest) {
				ProfileUserInfoArea(
					profileImage = null
				)

				ProfilePostArea(navigator = navigator)
			} else {
				// TODO 유저 정보 가져와서 표시
//				ProfileUserInfoArea(
//					profileImage = null
//				)
//
//				ProfilePostArea()
			}
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
private fun ProfileUserInfoArea(
	profileImage: String?,
	nickname: String = "익명의 쿼카"
) {
	Row (
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.padding(bottom = 16.dp)
	){
		GlideImage(
			modifier = Modifier.size(48.dp),
			model = profileImage ?: R.drawable.ic_profile_default,
			failure = placeholder(R.drawable.ic_profile_default),
			contentDescription = "프로필 이미지"
		)

		Spacer(modifier = Modifier.width(16.dp))

		Text(
			modifier = Modifier.align(Alignment.CenterVertically),
			text = nickname,
			fontSize = 20.sp,
			fontWeight = FontWeight.W700,
			color = Color.Black
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfilePostArea(
	isGuest: Boolean = true,
	navigator: LyfeNavigator
) {
	val pages = listOf("신청 사진", "고민 글")
	val pagerState = rememberPagerState { pages.size }
	val coroutineScope = rememberCoroutineScope()

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		TabRow(
			selectedTabIndex = pagerState.currentPage,
			modifier = Modifier.fillMaxWidth(),
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
		}

		if (isGuest) {
			// 게스트는 로그인 유도창 띄우기
			ProfileGuestLoginArea(navigator = navigator)
		}

		ProfilePostContent(
			isGuest = isGuest,
			pagerState = pagerState
		)
	}
}

@Composable
private fun ProfileGuestLoginArea(
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
//			navigator.navigate(
//				route = LyfeScreens.Login.route
//			)
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
//			navigator.navigate(
//				route = LyfeScreens.Login.route
//			)
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfilePostContent(
	isGuest: Boolean,
	pagerState: PagerState
) {
	HorizontalPager(
		modifier = Modifier.fillMaxSize(),
		state = pagerState
	) { page ->
		if (isGuest) return@HorizontalPager
		Text(
			text = (page+1).toString() ,
			fontSize = 30.sp
		)
	}
}