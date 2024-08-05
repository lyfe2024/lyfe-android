package com.lyfe.android.feature.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Body3
import com.lyfe.android.core.common.ui.theme.Caption3
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.Comment

@Composable
fun FeedDetailCommentItemView(
	modifier: Modifier = Modifier,
	comment: Comment,
	showReplyComment: Boolean = true,
	clickReplyText: (comment: Comment) -> Unit = {}
) {
	Column(
		modifier = modifier
	) {
		FeedDetailCommentUserRow(
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
			FeedDetailReplyCommentItemView(replyCommentList = comment.replyList)
		}
	}
}

@Preview
@Composable
private fun FeedDetailCommentItemViewPreview() {
	FeedDetailCommentItemView(
		comment = Comment(
			content = "hello",
			user = Comment.CommentUser(username = "user"),
			replyList = listOf(
				Comment(
					content = "reply",
					user = Comment.CommentUser(username = "user")
				)
			)
		)
	)
}