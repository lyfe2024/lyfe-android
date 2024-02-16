package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.LocalTokenDataSource
import com.lyfe.android.core.data.datasource.RemoteUserDataSource
import com.lyfe.android.core.data.mapper.toDomain
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
	private val remoteUserDataSource: RemoteUserDataSource,
	private val localTokenDataSource: LocalTokenDataSource
) : UserRepository {

	override suspend fun updateSignUpToken(signUpToken: String) {
		localTokenDataSource.updateSignUpToken(signUpToken)
	}

	override suspend fun updateAccessToken(accessToken: String) {
		localTokenDataSource.updateAccessToken(accessToken)
	}

	override suspend fun updateRefreshToken(refreshToken: String) {
		localTokenDataSource.updateRefreshToken(refreshToken)
	}

	override fun getSignUpToken(): Flow<String> {
		return localTokenDataSource.getSignUpToken()
	}

	override fun getAccessToken(): Flow<String> {
		return localTokenDataSource.getAccessToken()
	}

	override fun getRefreshToken(): Flow<String> {
		return localTokenDataSource.getRefreshToken()
	}

	override suspend fun deleteSignUpToken() {
		localTokenDataSource.deleteSignUpToken()
	}

	override suspend fun deleteAllToken() {
		localTokenDataSource.deleteAllToken()
	}

	override fun getUserInfo(): Flow<User> = flow {
		when (val response = remoteUserDataSource.getUserInfo()) {
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

	override suspend fun fetchIsNicknameDuplicated(nickname: String) = flow {
		when (val response = remoteUserDataSource.checkNicknameDuplicated(nickname)) {
			is Result.Success -> {
				emit(response.body!!.result.isAvailable)
			}
			is Result.Failure -> {
				throw RuntimeException(response.error)
			}
			is Result.NetworkError -> {
				throw RuntimeException(response.exception)
			}
			is Result.Unexpected -> {
				throw Exception(response.t)
			}
		}
	}

	override suspend fun putUserInfo(
		nickname: String,
		profileUrl: String,
		width: Int,
		height: Int
	) = flow {
		when (val response = remoteUserDataSource.putUserInfo(nickname, profileUrl, width, height)) {
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
	}

	override fun getUserBoard(lastId: Int?): Flow<Pair<List<Feed>, Page>> = flow {
		when (val response = remoteUserDataSource.getUserBoard(lastId)) {
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
				throw Exception(response.t)
			}
		}
	}.flowOn(ioDispatcher)
}