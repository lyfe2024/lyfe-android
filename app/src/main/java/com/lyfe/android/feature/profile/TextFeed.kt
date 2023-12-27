package com.lyfe.android.feature.profile

data class TextFeed(
    val feedId: Long,
    val title: String,
    val content: String,
    val date: String,
    val userId: Long,
    val userName: String,
    val whiskyCount: Int,
    val commentCount: Int,
    val isLike: Boolean
)
