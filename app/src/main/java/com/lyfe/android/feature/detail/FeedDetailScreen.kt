package com.lyfe.android.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeDialog
import com.lyfe.android.core.common.ui.component.LyfeImageView
import com.lyfe.android.core.common.ui.component.LyfeTextField
import com.lyfe.android.core.common.ui.theme.Body2
import com.lyfe.android.core.common.ui.theme.Body3
import com.lyfe.android.core.common.ui.theme.Button2
import com.lyfe.android.core.common.ui.theme.Button3
import com.lyfe.android.core.common.ui.theme.Caption3
import com.lyfe.android.core.common.ui.theme.Caption4
import com.lyfe.android.core.common.ui.theme.Grey10
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.theme.Grey400
import com.lyfe.android.core.common.ui.theme.H4
import com.lyfe.android.core.common.ui.theme.Main100
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.Title1
import com.lyfe.android.core.common.ui.theme.Title3
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.BoardDetail
import com.lyfe.android.core.model.Comment
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.core.navigation.navigator.LyfeNavigatorImpl
import com.lyfe.android.feature.feed.model.FeedCommentInputType

@Composable
fun FeedDetailRouter(
	navigator: LyfeNavigator = LyfeNavigatorImpl(),
	viewModel: FeedDetailViewModel = hiltViewModel()
) {
	val feedDetailUiState by viewModel.feedDetailUiState.collectAsStateWithLifecycle()

	FeedDetailScreen(
		feedDetailUiState = feedDetailUiState,
		fetchingNextCommentList = viewModel::fetchingCommentList,
		onNavigateUp = navigator::navigateUp
	)
}

@Composable
fun FeedDetailScreen(
	modifier: Modifier = Modifier,
	feedDetailUiState: FeedDetailUiState = FeedDetailUiState.Loading,
	fetchingNextCommentList: () -> Unit = {},
	onNavigateUp: () -> Unit = {}
) {
	var topBarHeight by remember { mutableStateOf(0.dp) }
	var commentInputAreaHeight by remember { mutableStateOf(0.dp) }
	val density = LocalDensity.current
	var isShowChatDialog by remember { mutableStateOf(false) }
	var commentInputType: FeedCommentInputType by remember { mutableStateOf(FeedCommentInputType.Idle) }

	Box(
		modifier = modifier
			.fillMaxSize()
	) {
		FeedDetailTopBar(
			modifier = Modifier
				.zIndex(1f)
				.onGloballyPositioned {
					topBarHeight = with(density) { it.size.height.toDp() }
				}
				.padding(horizontal = 20.dp, vertical = 16.dp),
			toggleOptionDialog = { },
			navigateUp = onNavigateUp
		)

		when (feedDetailUiState) {
			is FeedDetailUiState.Loading -> {}
			is FeedDetailUiState.Success -> {
				FeedDetailContent(
					modifier = modifier,
					topBarHeight = topBarHeight,
					commentInputAreaHeight = commentInputAreaHeight,
					feedDetail = feedDetailUiState.boardDetail,
					feedCommentList = feedDetailUiState.commentList,
					fetchingNextCommentList = fetchingNextCommentList,
					clickReplyText = { comment ->
						isShowChatDialog = true
						commentInputType = FeedCommentInputType.ReplyInput(comment = comment)
					}
				)
			}

			is FeedDetailUiState.Error -> {}
		}

		FeedCommentInputArea(
			modifier = Modifier
				.background(Color.White)
				.align(Alignment.BottomCenter)
				.onGloballyPositioned {
					with(density) {
						commentInputAreaHeight = it.size.height.toDp()
					}
				},
			clickCommentInputArea = {
				isShowChatDialog = true
				commentInputType = FeedCommentInputType.CommentInput
			}
		)

		if (commentInputType != FeedCommentInputType.Idle) {
			LyfeDialog(
				isShow = isShowChatDialog,
				onDismissRequest = {
					commentInputType = FeedCommentInputType.Idle
					isShowChatDialog = false
				}
			) {
				FeedCommentInputDialogContent(
					commentInputType = commentInputType
				)
			}
		}
	}
}

@Composable
private fun FeedDetailContent(
	modifier: Modifier,
	topBarHeight: Dp,
	commentInputAreaHeight: Dp,
	feedDetail: BoardDetail,
	feedCommentList: List<Comment>,
	threshold: Int = 10,
	fetchingNextCommentList: () -> Unit,
	clickReplyText: (comment: Comment) -> Unit
) {
	LazyColumn(
		modifier = modifier
	) {
		item {
			FeedDetailView(
				topBarHeight = topBarHeight,
				feedDetail = feedDetail
			)
		}

		itemsIndexed(
			items = feedCommentList
		) { index, it ->
			if ((index + threshold) >= feedCommentList.size) {
				fetchingNextCommentList()
			}

			key(index) {
				CommentItemView(
					modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
					comment = it,
					clickReplyText = clickReplyText
				)
			}
		}

		item {
			Spacer(
				modifier = Modifier
					.fillMaxWidth()
					.height(commentInputAreaHeight)
			)
		}
	}
}

@Composable
private fun FeedDetailView(
	topBarHeight: Dp,
	feedDetail: BoardDetail
) {
	Spacer(
		modifier = Modifier
			.fillMaxWidth()
			.height(topBarHeight)
	)

	Text(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 20.dp, end = 20.dp, bottom = 16.dp),
		text = feedDetail.content,
		style = H4,
		color = Main500
	)

	ImageFeedDetailInfoView(
		imageUrl = feedDetail.imageUrl,
		content = feedDetail.title
	)

	FeedDetailUserRow(
		cheersCnt = feedDetail.whiskyCount.toInt(),
		commentCnt = feedDetail.commentCount.toInt(),
		profileImg = feedDetail.user.profileImage,
		userName = feedDetail.user.name,
		date = feedDetail.updatedAt,
		onWhiskyClick = {}
	)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun FeedDetailUserRow(
	modifier: Modifier = Modifier,
	cheersCnt: Int,
	commentCnt: Int,
	profileImg: String,
	userName: String,
	date: String,
	onWhiskyClick: () -> Unit = {}
) {
	Row(
		modifier = modifier
			.padding(horizontal = 20.dp, vertical = 12.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Row(
			modifier = Modifier.clickableSingle { onWhiskyClick() },
			horizontalArrangement = Arrangement.spacedBy(4.dp)
		) {
			Icon(
				modifier = Modifier.size(24.dp),
				painter = painterResource(id = R.drawable.ic_glass_cheers),
				contentDescription = "ic_glass_cheers",
				tint = Color.Black
			)

			Text(
				text = cheersCnt.toString(),
				style = Button2,
				color = Color.Black
			)
		}

		Spacer(modifier = Modifier.width(8.dp))

		Icon(
			modifier = Modifier.size(24.dp),
			painter = painterResource(id = R.drawable.ic_comment),
			contentDescription = "ic_glass_cheers",
			tint = Color.Black
		)

		Spacer(modifier = Modifier.width(2.dp))

		Text(
			text = "댓글 $commentCnt",
			style = Body3
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
				style = Title3,
				color = Color.Black
			)

			Text(
				text = date,
				style = Caption4,
				color = Grey400
			)
		}
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImageFeedDetailInfoView(
	modifier: Modifier = Modifier,
	imageUrl: String,
	content: String
) {
	Box(
		modifier = modifier
			.fillMaxWidth()
			.aspectRatio(1f)
	) {
		LyfeImageView(
			modifier = Modifier.fillMaxSize(),
			imageUrl = imageUrl,
			contentDescription = "feed_detail_image_content",
			showBlurOnImage = true
		)

		Text(
			modifier = Modifier
				.fillMaxWidth()
				.align(Alignment.BottomCenter)
				.padding(horizontal = 20.dp, vertical = 16.dp),
			text = content,
			style = Title1,
			color = Color.White
		)
	}
}

@Composable
private fun FeedDetailTopBar(
	modifier: Modifier = Modifier,
	toggleOptionDialog: () -> Unit,
	navigateUp: () -> Unit
) {
	Row(
		modifier = modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			modifier = Modifier
				.size(24.dp)
				.clickableSingle { navigateUp() },
			painter = painterResource(id = R.drawable.ic_arrow_back),
			contentDescription = "ic_arrow_back",
			tint = Color.Black
		)

		Icon(
			modifier = Modifier
				.size(24.dp)
				.clickableSingle { toggleOptionDialog() },
			painter = painterResource(id = R.drawable.ic_quill_meatballs),
			contentDescription = "ic_quill_meatballs",
			tint = Color.Black
		)
	}
}

@Composable
private fun FeedCommentInputArea(
	modifier: Modifier = Modifier,
	clickCommentInputArea: () -> Unit = {}
) {
	Column(
		modifier = modifier.fillMaxWidth()
			.clickableSingle { clickCommentInputArea() }
	) {
		Spacer(
			modifier = Modifier
				.fillMaxWidth()
				.height(8.dp)
				.background(Grey10)
		)

		Box(
			modifier = Modifier
				.padding(vertical = 8.dp, horizontal = 12.dp)
		) {
			FeedCommentInputBox()
		}
	}
}

@Composable
private fun FeedCommentInputBox(
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
			.fillMaxWidth()
			.border(width = 1.dp, color = Grey200, shape = RoundedCornerShape(8.dp))
			.padding(vertical = 12.dp, horizontal = 12.dp)
	) {
		Text(
			text = "댓글을 남겨주세요",
			style = Body2,
			color = Grey200
		)
	}
}

@Composable
private fun FeedCommentInputDialogContent(
	modifier: Modifier = Modifier,
	commentInputType: FeedCommentInputType
) {
	var comment by remember { mutableStateOf("") }

	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			if (commentInputType is FeedCommentInputType.ReplyInput) {
				Row(
					horizontalArrangement = Arrangement.spacedBy(2.dp)
				) {
					Text(
						modifier = Modifier
							.background(color = Main100, shape = RoundedCornerShape(4.dp))
							.padding(vertical = 4.dp, horizontal = 1.dp),
						text = "@${commentInputType.comment.user.username}",
						style = Caption4,
						color = Main500
					)

					Text(text = "님에게 답글 남기는 중")
				}
			} else {
				Text(
					text = "댓글 작성 중...",
					style = Caption3,
					color = Color.Black
				)
			}
		}

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.border(width = 1.dp, color = Main500, shape = RoundedCornerShape(8.dp))
				.padding(vertical = 8.dp, horizontal = 12.dp),
			horizontalArrangement = Arrangement.spacedBy(4.dp)
		) {
			LyfeTextField(
				modifier = Modifier.weight(1f),
				textStyle = Body3,
				requestFocus = true,
				text = comment,
				onTextChange = { comment = it }
			)

			Box(
				modifier = Modifier.wrapContentWidth().wrapContentHeight()
					.align(Alignment.Bottom)
			) {
				Image(
					modifier = Modifier.align(Alignment.BottomCenter),
					painter = painterResource(id = R.drawable.ic_comment_enter_main500),
					contentDescription = "comment_enter"
				)
			}
		}
	}
}

@Composable
fun CommentItemView(
	modifier: Modifier = Modifier,
	comment: Comment,
	showReplyComment: Boolean = true,
	clickReplyText: (comment: Comment) -> Unit = {}
) {
	Column(
		modifier = modifier
	) {
		UserRow(
			profileImg = comment.user.profileImg,
			userName = comment.user.username,
			date = comment.updatedAt,
			clickOption = {}
		)

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			text = comment.content,
			style = Body3,
			color = Color.Black,
			overflow = TextOverflow.Ellipsis,
			maxLines = 2
		)

		if (showReplyComment) {
			Spacer(modifier = Modifier.height(8.dp))

			Text(
				modifier = Modifier
					.clickableSingle { clickReplyText(comment) },
				text = stringResource(id = R.string.comment_reply_text),
				style = Caption3,
				color = Grey300
			)
		}

		if (comment.replyList.isNotEmpty()) {
			ReplyCommentItemView(replyCommentList = comment.replyList)
		}
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun UserRow(
	modifier: Modifier = Modifier,
	profileImg: String,
	userName: String,
	date: String,
	clickOption: () -> Unit = {}
) {
	Row(
		modifier = modifier
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		Box(
			modifier = Modifier
				.size(24.dp)
				.clip(CircleShape)
		) {
			GlideImage(
				modifier = Modifier.fillMaxSize(),
				model = profileImg,
				contentDescription = "profileImg",
				contentScale = ContentScale.Crop
			)
		}

		Spacer(modifier = Modifier.width(8.dp))

		Text(
			text = userName,
			style = Button3,
			color = Color.Black
		)

		Spacer(modifier = Modifier.width(8.dp))

		Text(
			text = date,
			style = Caption4,
			color = Grey300
		)

		Spacer(modifier = Modifier.weight(1f))

		Icon(
			modifier = Modifier
				.size(24.dp)
				.clickableSingle { clickOption() },
			painter = painterResource(id = R.drawable.ic_menu_more),
			contentDescription = "ic_menu_more",
			tint = Grey300
		)
	}
}

@Composable
private fun ReplyCommentItemView(
	modifier: Modifier = Modifier,
	replyCommentList: List<Comment>
) {
	replyCommentList.forEachIndexed { _, comment ->
		Spacer(modifier = Modifier.height(8.dp))

		Row(
			modifier = modifier
		) {
			Image(
				modifier = Modifier.size(16.dp),
				painter = painterResource(id = R.drawable.ic_arrow_comment_reply),
				contentDescription = "ic_arrow_comment_reply"
			)

			Spacer(modifier = Modifier.width(8.dp))

			CommentItemView(
				comment = comment,
				showReplyComment = false
			)
		}
	}
}

@Preview
@Composable
private fun Preview_FeedDetailScreen() {
	FeedDetailScreen()
}