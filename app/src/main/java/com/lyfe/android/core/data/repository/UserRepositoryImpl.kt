package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.UserDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.model.PutUserInfoResponse
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.UserRepository
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.Page
import com.lyfe.android.core.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
	private val userDataSource: UserDataSource
) : UserRepository {

	override suspend fun fetchIsNicknameDuplicated(nickname: String): Result<CheckNicknameResponse> {
		return userDataSource.checkNicknameDuplicated(nickname)
	}

	override fun getUserInfo(): Flow<User> = flow {
		when (val response = userDataSource.getUserInfo()) {
			is Result.Success -> {
				emit(response.body!!.result.toDomain())
			}
			is Result.Failure -> {
				throw RuntimeException(response.error)
			}
			is Result.NetworkError -> {
				throw RuntimeException(response.exception)
			}
			is Result.Unexpected -> {
				throw RuntimeException(response.t)
			}
		}
	}.flowOn(ioDispatcher)

	override suspend fun putUserInfo(
		nickname: String,
		profileUrl: String,
		width: Int,
		height: Int
	): Result<PutUserInfoResponse> {
		return userDataSource.putUserInfo(nickname, profileUrl, width, height)
	}

	override fun getUserBoard(lastId: Int?): Flow<Pair<List<Feed>, Page>> = flow {
		when (val response = userDataSource.getUserBoard(lastId)) {
			is Result.Success -> {
				val result = response.body!!.result
				emit(Pair(result.boardPictureList.map { it.toDomain() }, result.page.toDomain()))
			}
			is Result.Failure -> {
				throw RuntimeException(response.error)
			}
			is Result.NetworkError -> {
				throw RuntimeException(response.exception)
			}
			is Result.Unexpected -> {
				throw RuntimeException(response.t)
			}
		}
	}.flowOn(ioDispatcher)
}