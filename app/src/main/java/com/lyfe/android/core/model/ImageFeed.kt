package com.lyfe.android.core.model

data class ImageFeed(
    val id: Int,
    val nickname: String = "유저이름",
    val title: String = "사진 제목 텍스트\n두 줄까지 들어가고 나머지는 넘어가게",
    val reactionCount: Int = 40,
    val commentCount: Int = 8
)
