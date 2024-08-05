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
import com.lyfe.android.feature.detail.component.FeedCommentInputArea
import com.lyfe.android.feature.detail.component.FeedDetailCommentItemView
import com.lyfe.android.feature.detail.component.FeedDetailImageInfoView
import com.lyfe.android.feature.detail.component.FeedDetailReplyCommentItemView
import com.lyfe.android.feature.detail.component.FeedDetailTopBar
import com.lyfe.android.feature.detail.component.FeedDetailTopicBar
import com.lyfe.android.feature.detail.component.FeedDetailUserInfoRow
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
		createComment = viewModel::createComment,
		onNavigateUp = navigator::navigateUp
	)
}

@Composable
fun FeedDetailScreen(
	modifier: Modifier = Modifier,
	feedDetailUiState: FeedDetailUiState = FeedDetailUiState.Loading,
	fetchingNextCommentList: () -> Unit = {},
	createComment: (content: String) -> Unit = {},
	onWhiskyClick: (feedId: Long) -> Unit = {},
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
			modifier = Modifier.zIndex(1f),
			onGloballyPositioned = {
				topBarHeight = with(density) { it.size.height.toDp() }
			},
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
					},
					onWhiskyClick = onWhiskyClick
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
					commentInputType = commentInputType,
					createComment = createComment
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
	clickReplyText: (comment: Comment) -> Unit,
	onWhiskyClick: (feedId: Long) -> Unit
) {
	LazyColumn(
		modifier = modifier
	) {
		item {
			FeedDetailView(
				topBarHeight = topBarHeight,
				feedDetail = feedDetail,
				onWhiskyClick = onWhiskyClick
			)
		}

		itemsIndexed(
			items = feedCommentList
		) { index, it ->
			if ((index + threshold) >= feedCommentList.size) {
				fetchingNextCommentList()
			}

			key(index) {
				FeedDetailCommentItemView(
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
	modifier: Modifier = Modifier,
	topBarHeight: Dp,
	feedDetail: BoardDetail,
	onWhiskyClick: (feedId: Long) -> Unit = {}
) {
	Column(
		modifier = modifier
			.padding(top = topBarHeight)
	) {
		FeedDetailTopicBar(
			topic = feedDetail.topic
		)

		FeedDetailImageInfoView(
			imageUrl = feedDetail.content,
			content = feedDetail.title
		)

		FeedDetailUserInfoRow(
			cheersCnt = feedDetail.whiskyCount.toIntOrNull() ?: 0,
			commentCnt = feedDetail.commentCount.toIntOrNull() ?: 0,
			profileImg = feedDetail.user.profileImage,
			userName = feedDetail.user.name,
			date = feedDetail.updatedAt,
			onWhiskyClick = { onWhiskyClick(feedDetail.id) }
		)
	}
}

@Composable
private fun FeedCommentInputDialogContent(
	modifier: Modifier = Modifier,
	commentInputType: FeedCommentInputType,
	createComment: (content: String) -> Unit = {}
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
				modifier = Modifier
					.wrapContentWidth()
					.wrapContentHeight()
					.align(Alignment.Bottom)
					.clickableSingle { createComment(comment) }
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

@Preview
@Composable
private fun Preview_FeedDetailScreen() {
	FeedDetailScreen()
}