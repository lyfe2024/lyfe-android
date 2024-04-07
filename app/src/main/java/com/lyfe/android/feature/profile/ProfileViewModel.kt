package com.lyfe.android.feature.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.domain.usecase.GetUserBoardUseCase
import com.lyfe.android.core.domain.usecase.GetUserInfoUseCase
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val getUserInfoUseCase: GetUserInfoUseCase,
	private val getUserBoardUseCase: GetUserBoardUseCase
) : ViewModel() {
	var uiState by mutableStateOf<ProfileUiState>(ProfileUiState.Loading)
		private set

	private val _user = MutableStateFlow(User())
	val user get() = _user.value

	private val _textFeedList = MutableStateFlow<List<Feed>>(emptyList())
	val textFeedList get() = _textFeedList.asStateFlow()

	private val _imageFeedList = MutableStateFlow<List<Feed>>(emptyList())
	val imageFeedList get() = _imageFeedList.asStateFlow()

	private var imageCursorId = 0L
	private var textCursorId = 0L

	fun getUserInfo() = viewModelScope.launch {
		getUserInfoUseCase().onEach {
			uiState = ProfileUiState.Loading
		}.catch {
			uiState = ProfileUiState.Failure
		}.collect {
			_user.value = it
			uiState = ProfileUiState.UserSuccess
		}
	}

	fun fetchImageFeedList() = viewModelScope.launch {
		getUserBoardUseCase(
			boardType = "BOARD_PICTURE",
			cursorId = imageCursorId
		).catch {
			LogUtil.e("ProfileViewModel", it.message ?: "에러 메세지가 없습니다.")
		}.collect { feeds ->
			if (feeds.isEmpty()) {
				return@collect
			}
			imageCursorId = feeds.first().feedId
			_imageFeedList.update {
				it.plus(feeds)
			}
		}
	}

	fun fetchTextFeedList() = viewModelScope.launch {
		getUserBoardUseCase(
			boardType = "BOARD",
			cursorId = textCursorId
		).catch {
			LogUtil.e("ProfileViewModel", it.message ?: "에러 메세지가 없습니다.")
		}.collect { feeds ->
			if (feeds.isEmpty()) {
				return@collect
			}
			textCursorId = feeds.first().feedId
			_textFeedList.update {
				it.plus(feeds)
			}
		}
	}
}