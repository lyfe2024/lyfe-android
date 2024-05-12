package com.lyfe.android.feature.feed.model

sealed interface FeedCommentInputType {

	object Idle : FeedCommentInputType
	object CommentInput : FeedCommentInputType
	data class ReplyInput(val comment: com.lyfe.android.core.model.Comment) : FeedCommentInputType
}