package com.lyfe.android.feature.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.TextField
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.permission.NeededPermission
import com.lyfe.android.core.common.ui.permission.PermissionAlertDialogs
import com.lyfe.android.core.common.ui.permission.PermissionsCheckScreen
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.common.ui.util.noRippleClickable
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.album.SelectImageKey
import com.lyfe.android.ui.theme.BtnLightGrayColor

@Composable
fun PostScreen(
	navigator: LyfeNavigator,
	navHostController: NavHostController,
	viewModel: PostViewModel = hiltViewModel()
) {
	var isNavigateToSelectAlbum by remember { mutableStateOf(false) }

	LaunchedEffect(navHostController) {
		val selectedImage =
			navHostController.getBackStackEntry(LyfeScreens.Post.name).savedStateHandle.get<String>(SelectImageKey)

		viewModel.setSelectedImage(selectedImage ?: "")
	}

	LaunchedEffect(isNavigateToSelectAlbum) {
		if (!isNavigateToSelectAlbum) return@LaunchedEffect

		navigator.navigate(LyfeScreens.SelectAlbum.name)
		viewModel.setUiEventIdle()
	}

	when (viewModel.uiState) {
		is PostUiState.Success -> {
			val image = (viewModel.uiState as PostUiState.Success).selectedImage

			PostScreenDefault(
				viewModel = viewModel,
				image = image,
				title = viewModel.title,
				changeTitle = { change ->
					viewModel.title = change
				},
				isBtnClickable = viewModel.title.isNotEmpty() && image.isNotEmpty(),
				onClickBackBtn = {
					navigator.navigateUp()
				}
			)
		}
	}

	HandleUiEvent(
		event = (viewModel.uiState as PostUiState.Success).event,
		neededPermissions = viewModel.neededPermissions,
		checkPermissionResult = { passedPermissionList, failedPermissionList ->
			viewModel.checkPermissionResult(passedPermissionList, failedPermissionList)
		},
		onPermissionSuccess = { permissionList -> viewModel.addAllowedPermissions(permissionList) },
		onDialogDismiss = { viewModel.setUiEventIdle() },
		navigateToSelectAlbum = {
			isNavigateToSelectAlbum = true
		}
	)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun PostScreenDefault(
	viewModel: PostViewModel,
	image: String = "",
	title: String,
	changeTitle: (title: String) -> Unit,
	onClickBackBtn: () -> Unit,
	isBtnClickable: Boolean = false
) {
	Column(
		modifier = Modifier
			.padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 24.dp)
	) {
		PostTopBar(modifier = Modifier.wrapContentWidth()) { onClickBackBtn() }

		Spacer(modifier = Modifier.height(16.dp))

		PostTitleBox(modifier = Modifier.wrapContentWidth())

		Spacer(modifier = Modifier.height(16.dp))

		if (image.isEmpty()) {
			UploadingPhotoBox(
				modifier = Modifier.wrapContentSize()
			) {
				viewModel.checkPermission()
			}
		} else {
			GlideImage(
				modifier = Modifier
					.fillMaxWidth()
					.aspectRatio(1f),
				model = image,
				contentDescription = ""
			) {
				it.centerCrop()
			}
		}

		Spacer(modifier = Modifier.height(24.dp))
		TypingPostTitleBox(
			modifier = Modifier
				.weight(1f),
			postTitle = title
		) { title ->
			changeTitle(title)
		}

		Spacer(modifier = Modifier.height(16.dp))

		LyfeButton(
			modifier = Modifier
				.fillMaxWidth()
				.height(48.dp),
			text = "게시",
			buttonType = if (isBtnClickable) {
				LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
			} else {
				LyfeButtonType.TC_GREY200_BG_GREY50_SC_GREY200
			},
			verticalPadding = 12.dp,
			horizontalPadding = 24.dp,
			isClearIconShow = false
		) {

		}
	}
}

@Composable
private fun HandleUiEvent(
	event: PostUiEvent,
	neededPermissions: Array<String>,
	checkPermissionResult: (
		passedPermissionList: List<NeededPermission>,
		failedPermissionList: List<NeededPermission>
	) -> Unit,
	onPermissionSuccess: (permissionList: List<NeededPermission>) -> Unit,
	onDialogDismiss: () -> Unit,
	navigateToSelectAlbum: () -> Unit
) {
	when (event) {
		is PostUiEvent.CheckPermission -> {
			PermissionsCheckScreen(
				neededPermissions = neededPermissions
			) { passedPermissionList, failedPermissionList ->
				checkPermissionResult(passedPermissionList, failedPermissionList)
			}
		}

		is PostUiEvent.ShowPermissionAlertDialog -> {
			PermissionAlertDialogs(
				failedPermissionList = event.failedPermissionList,
				permissionSuccess = { permissionList ->
					onPermissionSuccess(permissionList)
				},
				onDismiss = { onDialogDismiss() }
			)
		}

		is PostUiEvent.MoveToSelectAlbum -> {
			navigateToSelectAlbum()
		}

		else -> {}
	}
}

@Composable
private fun PostTitleBox(
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
	) {
		Text(
			text = "사진 신청",
			style = TextStyle(
				fontSize = 24.sp,
				lineHeight = 36.sp,
				fontWeight = FontWeight(weight = 700),
				color = Color(color = 0xFF000000)
			)
		)
	}
}

@Composable
private fun PostTopBar(
	modifier: Modifier = Modifier,
	onClickBackBtn: () -> Unit
) {
	Box(
		modifier = modifier
	) {
		Image(
			modifier = Modifier
				.noRippleClickable { onClickBackBtn() },
			painter = painterResource(id = R.drawable.ic_arror_back_black),
			contentDescription = "post_back_btn"
		)
	}
}

@Composable
private fun UploadingPhotoBox(
	modifier: Modifier = Modifier,
	clickPhotoBox: () -> Unit
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.height(158.dp)
			.background(color = BtnLightGrayColor, shape = RoundedCornerShape(size = 10.dp))
			.clickableSingle {
				clickPhotoBox()
			},
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_plus_black),
			contentDescription = "ic_plus_black"
		)

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			text = "사진 업로드",
			style = TextStyle(
				fontSize = 14.sp,
				lineHeight = 21.sp,
				fontWeight = FontWeight.W600,
				color = Color.Black
			)
		)
	}
}

@Composable
private fun TypingPostTitleBox(
	modifier: Modifier = Modifier,
	postTitle: String,
	onPostTitleChange: (String) -> Unit
) {
	Column(
		modifier = modifier
	) {
		Text(
			modifier = Modifier,
			text = "제목",
			style = TextStyle(
				fontSize = 16.sp,
				lineHeight = 24.sp,
				fontWeight = FontWeight(weight = 700),
				color = Color(color = 0xFF000000)
			)
		)
		Spacer(modifier = Modifier.height(8.dp))
		TextField(
			modifier = Modifier.fillMaxWidth(),
			text = postTitle,
			onTextChange = onPostTitleChange,
			placeHolder = {
				Text(
					text = "제목을 입력해주세요",
					style = TextStyle(
						fontSize = 16.sp,
						lineHeight = 24.sp,
						fontWeight = FontWeight(weight = 500),
						color = Color(color = 0xFFC4C4C4)
					)
				)
			}
		)
	}
}