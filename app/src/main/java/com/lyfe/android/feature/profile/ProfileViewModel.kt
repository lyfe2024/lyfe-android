package com.lyfe.android.feature.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lyfe.android.core.domain.usecase.GetUserBoardUseCase
import com.lyfe.android.core.domain.usecase.GetUserInfoUseCase
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.Page
import com.lyfe.android.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val getUserInfoUseCase: GetUserInfoUseCase,
	private val getUserBoardUseCase: GetUserBoardUseCase
) : ViewModel() {
	var uiState by mutableStateOf<ProfileUiState>(ProfileUiState.Loading)
		private set

	private val _user = mutableStateOf(User())
	val userInfo get() = _user.value

	private val _feedList = MutableStateFlow<List<Feed>>(emptyList())
	val feedList get() = _feedList.asStateFlow()

	private var lastPage: Page? = null

	init {
		getUserInfo()
		fetchFeedList()
	}

	fun getUserInfo() {
		getUserInfoUseCase().onStart {
			uiState = ProfileUiState.Loading
		}.onEach {
			_user.value = it
			uiState = ProfileUiState.UserSuccess
		}.catch {
			uiState = ProfileUiState.Failure
		}
	}

	fun fetchFeedList() {
		getUserBoardUseCase(lastPage?.number).onStart {
			uiState = ProfileUiState.Loading
		}.onEach { result ->
			val feeds = result.first
			lastPage = result.second
			_feedList.update {
				it.plus(feeds)
			}
		}.catch {
			uiState = ProfileUiState.Failure
		}
	}
}