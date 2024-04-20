package com.lyfe.android.core.data.mapper

import com.lyfe.android.core.data.model.BoardPicture
import com.lyfe.android.core.data.model.BoardDetail
import com.lyfe.android.core.data.model.PageInfo
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.Page

internal fun BoardDetail.toDomain(): Feed =
	Feed(
		feedId = this.id,
		title = this.title,
		content = this.content,
		topic = this.topic,
		feedImageUrl = this.imageUrl,
		date = this.updatedAt,
		userId = this.user.id,
		userName = this.user.username,
		userProfileImgUrl = this.user.profile,
		whiskyCount = this.whiskyCount,
		commentCount = this.commentCount,
		isLike = false
	)

internal fun PageInfo.toDomain(): Page =
	Page(
		size = this.size,
		number = this.number,
		totalElements = this.totalElements,
		totalPages = this.totalPages
	)