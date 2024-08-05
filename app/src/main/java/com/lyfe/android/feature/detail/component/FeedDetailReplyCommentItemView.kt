package com.lyfe.android.feature.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lyfe.android.R
import com.lyfe.android.core.model.Comment

@Composable
fun FeedDetailReplyCommentItemView(
	modifier: Modifier = Modifier,
	replyCommentList: List<Comment>
) {
	Column(
		modifier = modifier
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

				FeedDetailCommentItemView(
					comment = comment,
					showReplyComment = false
				)
			}
		}
	}
}

@Preview
@Composable
private fun FeedDetailReplyCommentItemViewPreview() {
	FeedDetailReplyCommentItemView(
		replyCommentList = listOf(
			Comment(
				content = "hello",
				user = Comment.CommentUser(
					username = "user"
				)
			),
			Comment(
				content = "hello",
				user = Comment.CommentUser(
					username = "user"
				)
			),
		)
	)
}