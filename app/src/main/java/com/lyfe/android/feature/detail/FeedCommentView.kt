package com.lyfe.android.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.Comment

@Composable
internal fun FeedCommentView(
	modifier: Modifier = Modifier,
	commentList: List<Comment>
) {
	Box(
		modifier = modifier
	) {
		commentList.forEachIndexed { index, it ->
			CommentItemView(
				modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
				comment = it
			)
		}
	}
}

@Composable
private fun ReplyCommentItemView(
	modifier: Modifier = Modifier,
	replyCommentList: List<Comment>
) {
	replyCommentList.forEachIndexed { index, comment ->
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

@Composable
private fun CommentItemView(
	modifier: Modifier = Modifier,
	comment: Comment,
	showReplyComment: Boolean = true
) {
	Column(
		modifier = modifier
	) {
		UserRow(
			profileImg = comment.profileImg,
			userName = comment.userName,
			date = comment.date,
			clickOption = {}
		)

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			text = comment.content,
			style = TextStyle(
				fontSize = 14.sp,
				lineHeight = 22.sp,
				fontFamily = pretenard,
				fontWeight = FontWeight.W500,
				color = Color.Black
			),
			overflow = TextOverflow.Ellipsis,
			maxLines = 2
		)

		if (showReplyComment) {
			Spacer(modifier = Modifier.height(8.dp))

			Text(
				text = stringResource(id = R.string.comment_reply_text),
				style = TextStyle(
					fontSize = 12.sp,
					lineHeight = 18.sp,
					fontFamily = pretenard,
					fontWeight = FontWeight.W400,
					color = Grey300
				)
			)
		}
		
		if (comment.replyCommentList.isNotEmpty()) {
			ReplyCommentItemView(replyCommentList = comment.replyCommentList)
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
			style = TextStyle(
				fontSize = 12.sp,
				lineHeight = 18.sp,
				fontFamily = pretenard,
				fontWeight = FontWeight.W600,
				color = Color.Black
			)
		)
		Spacer(modifier = Modifier.width(8.dp))
		Text(
			text = date,
			style = TextStyle(
				fontSize = 10.sp,
				lineHeight = 16.sp,
				fontFamily = pretenard,
				fontWeight = FontWeight.W400,
				color = Grey300
			)
		)
		Spacer(modifier = Modifier.weight(1f))
		Image(
			modifier = Modifier
				.size(24.dp)
				.clickableSingle {
					clickOption()
				},
			painter = painterResource(id = R.drawable.ic_menu_more),
			contentDescription = "ic_menu_more",
			colorFilter = ColorFilter.tint(Grey300)
		)
	}
}