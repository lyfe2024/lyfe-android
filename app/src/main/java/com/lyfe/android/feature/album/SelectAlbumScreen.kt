package com.lyfe.android.feature.album

import android.graphics.drawable.Drawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.rememberGlidePreloadingData
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.RoundedCornerButton
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.GalleryImage
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.ui.theme.BtnDarkColor
import com.lyfe.android.ui.theme.BtnLightGrayColor
import com.lyfe.android.ui.theme.DisabledBtnTextColor

const val SelectImageKey = "Select Image"

@Composable
fun SelectAlbumScreen(
	viewModel: SelectAlbumViewModel = hiltViewModel(),
	navigator: LyfeNavigator
) {
	val lazyGridState = rememberLazyGridState()

	Box(
		modifier = Modifier.fillMaxSize()
	) {
		when (viewModel.uiState) {
			is SelectAlbumUiState.Loading -> {
				CircularProgressIndicator(
					modifier = Modifier.size(30.dp),
					color = Color.Red
				)
			}

			is SelectAlbumUiState.Success -> {
				val images = (viewModel.uiState as SelectAlbumUiState.Success).images
				var selectedImageIdx by remember { mutableIntStateOf(-1) }
				var selectedImageUri by remember { mutableStateOf("") }

				SelectAlbumImagesBox(
					modifier = Modifier.fillMaxSize(),
					images = images,
					selectedImageIdx = selectedImageIdx,
					lazyGridState = lazyGridState
				) { index, image ->
					selectedImageIdx = index
					selectedImageUri = image
				}

				AnimatedVisibility(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 20.dp)
						.align(Alignment.BottomCenter)
						.background(Color.Transparent)
						.zIndex(1f),
					visible = !lazyGridState.isScrollInProgress,
					enter = slideInVertically(initialOffsetY = { it }),
					exit = slideOutVertically(targetOffsetY = { it })
				) {
					SelectAlbumBottomArea(
						isClickable = selectedImageUri.isNotEmpty()
					) {
						navigator.navigateBackWithResult(
							key = SelectImageKey,
							result = selectedImageUri,
							route = LyfeScreens.Post.name
						)
					}
				}
			}

			is SelectAlbumUiState.EmptyGalleryImages -> {
				EmptyGalleryImagesBox(
					modifier = Modifier.fillMaxSize()
				)
			}

			is SelectAlbumUiState.Error -> {}
		}
	}
}

@Composable
fun SelectAlbumBottomArea(
	modifier: Modifier = Modifier,
	isClickable: Boolean = false,
	onClick: () -> Unit
) {
	Column(modifier = modifier) {
		SelectAlbumButton(
			isClickable = isClickable
		) {
			onClick()
		}

		Spacer(modifier = Modifier.height(24.dp))
	}
}

@Composable
fun SelectAlbumButton(
	isClickable: Boolean = true,
	onClick: () -> Unit
) {
	val textColor = if (isClickable) Color.White else DisabledBtnTextColor

	RoundedCornerButton(
		modifier = Modifier
			.fillMaxWidth(),
		cornerRadius = 10.dp,
		horizontalPadding = 24.dp,
		verticalPadding = 12.dp,
		isClickable = isClickable,
		isClickableColor = BtnDarkColor,
		isNotClickableColor = BtnLightGrayColor,
		onClick = { onClick() }
	) {
		Text(
			text = "선택완료",
			style = TextStyle(
				fontSize = 16.sp,
				lineHeight = 24.sp,
				fontWeight = FontWeight.W700,
				color = textColor
			)
		)
	}
}

@Composable
fun SelectAlbumImagesBox(
	modifier: Modifier = Modifier,
	images: List<GalleryImage>,
	selectedImageIdx: Int = -1,
	lazyGridState: LazyGridState,
	selectImage: (index: Int, image: String) -> Unit
) {
	val requestBuilderTransform =
		{ item: GalleryImage, requestBuilder: RequestBuilder<Drawable> ->
			requestBuilder.load(item)
		}

	val thumbnailDimension = 50
	val thumbnailSize = Size(thumbnailDimension.toFloat(), thumbnailDimension.toFloat())

	val preloadingData = rememberGlidePreloadingData(
		data = images,
		preloadImageSize = thumbnailSize,
		requestBuilderTransform = requestBuilderTransform
	)

	val gridCellCount = 3

	Box {
		LazyVerticalGrid(
			modifier = modifier,
			columns = GridCells.Fixed(gridCellCount),
			verticalArrangement = Arrangement.spacedBy(4.dp),
			horizontalArrangement = Arrangement.spacedBy(4.dp),
			state = lazyGridState
		) {
			items(preloadingData.size, contentType = { it }) { index ->
				val (galleryImage, preloadRequestBuilder) = preloadingData[index]

				key(galleryImage) {
					GalleryImageView(
						image = galleryImage,
						isSelected = selectedImageIdx == index,
						preloadRequestBuilder = preloadRequestBuilder
					) { image ->
						selectImage(index, image)
					}
				}
			}
		}

		IndicatorBar(
			modifier = Modifier
				.align(Alignment.TopEnd)
				.zIndex(1f),
			listState = lazyGridState,
			columnSize = 3
		)
	}
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun GalleryImageView(
	image: GalleryImage,
	isSelected: Boolean = false,
	preloadRequestBuilder: RequestBuilder<Drawable>,
	selectImage: (image: String) -> Unit
) {
	val borderWidth = if (isSelected) 2.dp else 0.dp

	Box(
		modifier = Modifier
			.fillMaxSize()
			.aspectRatio(1f)
			.background(color = Color.LightGray)
			.border(border = BorderStroke(width = borderWidth, color = Color.Black), shape = RectangleShape)
			.clickableSingle {
				selectImage(image.imageUri)
			}
	) {
		if (isSelected) {
			Box(
				modifier = Modifier
					.align(Alignment.TopEnd)
					.padding(top = 9.dp, end = 9.dp)
					.zIndex(1f)
			) {
				CircleCheckBox()
			}
		}

		GlideImage(
			model = image.imageUri,
			contentDescription = "",
			contentScale = ContentScale.Crop
		) {
			it.thumbnail(preloadRequestBuilder)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
		}
	}
}

@Composable
private fun CircleCheckBox() {
	Box(
		modifier = Modifier
			.background(color = Color.Black, shape = CircleShape)
			.padding(4.dp)
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_check_white_12),
			contentDescription = "ic_check_white_24"
		)
	}
}

@Composable
private fun EmptyGalleryImagesBox(
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
	) {
		Text(
			text = "현재 갤러리에 이미지가 없습니다."
		)
	}
}