package com.lyfe.android.core.model

data class TextFeed(
    val id: Int,
    val title: String = "Feed Title",
    val content: String = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 만약 제대로 표시되지 않는다면 빨리 고쳐야함.",
    val createdAt: String = "몇 분 전",
    val reactionCount: Int = 32,
    val commentCount: Int = 50
)
