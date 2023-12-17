package com.lyfe.android.core.model

data class TextFeed(
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: String,
    val reactionCount: Int = 0,
    val commentCount: Int = 0
)
