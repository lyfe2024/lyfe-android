package com.lyfe.android.feature.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lyfe.android.core.model.Comment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedDetailViewModel @Inject constructor() : ViewModel() {
	var commentList by mutableStateOf<List<Comment>>(emptyList())
		private set

	fun fetchCommentList() {
		commentList = listOf(
			Comment(
				id = 0L,
				profileImg = "https://media.bunjang.co.kr/product/233392017_1_1692231420_w360.jpg",
				userName = "유저이름",
				date = "몇 분전",
				content = "여기는 내용 들어옵니다. 최대 2줄까지라고 되어 있는데 꼭 2줄 제한을 둬야 할까요? 안해도 괜찮을것 같습니다만.. 글자수로 제한을 둬야 하지 않을까 싶네요?",
				replyCommentList = listOf(
					Comment(
						id = 1L,
						profileImg = "https://media.bunjang.co.kr/product/233392017_1_1692231420_w360.jpg",
						userName = "유저이름2",
						date = "몇 분전",
						content = "asda41sd",
						replyCommentList = emptyList()
					),
					Comment(
						id = 2L,
						profileImg = "https://media.bunjang.co.kr/product/233392017_1_1692231420_w360.jpg",
						userName = "유저이름3",
						date = "몇 분전",
						content = "asdas123d",
						replyCommentList = emptyList()
					),
					Comment(
						id = 3L,
						profileImg = "https://media.bunjang.co.kr/product/233392017_1_1692231420_w360.jpg",
						userName = "유저이름4",
						date = "몇 분전",
						content = "asdasd23",
						replyCommentList = emptyList()
					)
				)
			),
			Comment(
				id = 5L,
				profileImg = "https://media.bunjang.co.kr/product/233392017_1_1692231420_w360.jpg",
				userName = "다음 유저",
				date = "몇 분전",
				content = "123456789",
				replyCommentList = emptyList()
			)
		)
	}
}