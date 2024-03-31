package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.LocalUserDataSource
import com.lyfe.android.core.data.datasource.RemoteUserDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.ApiResultException
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
	private val localUserDataSource: LocalUserDataSource
) : UserRepository {
	override suspend fun updateSocialType(socialType: String) {
		localUserDataSource.updateSocialType(socialType = socialType)
	}

	override fun getSocialType(): Flow<String> {
		return localUserDataSource.getSocialType()
	}

	override fun getUserInfo(): Flow<User> = flow {
		when (val response = remoteUserDataSource.getUserInfo()) {
			is Result.Success -> {
				val body = response.body ?: throw ApiResultException()
				emit(body.result.toDomain())
			}
			is Result.Failure -> {
				throw ApiResultException(response.error)
			}
			is Result.NetworkError -> {
				throw response.exception
			}
			is Result.Unexpected -> {
				throw response.t ?: ApiResultException()
			}
		}
	}.flowOn(ioDispatcher)

	override suspend fun fetchIsNicknameDuplicated(nickname: String) = flow {
		when (val response = remoteUserDataSource.checkNicknameDuplicated(nickname)) {
			is Result.Success -> {
				val body = response.body ?: throw ApiResultException()
				emit(body.result.isAvailable)
			}
			is Result.Failure -> {
				throw ApiResultException(response.error)
			}
			is Result.NetworkError -> {
				throw response.exception
			}
			is Result.Unexpected -> {
				throw ApiResultException()
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
				val body = response.body ?: throw ApiResultException()
				emit(body.result.toDomain())
			}
			is Result.Failure -> {
				throw ApiResultException(response.error)
			}
			is Result.NetworkError -> {
				throw response.exception
			}
			is Result.Unexpected -> {
				throw response.t ?: ApiResultException()
			}
		}
	}

	override fun getUserBoard(lastId: Int?): Flow<Pair<List<Feed>, Page>> = flow {
		when (val response = remoteUserDataSource.getUserBoard(lastId)) {
			is Result.Success -> {
				val result = response.body?.result ?: throw ApiResultException()
				emit(Pair(result.boardPictureList.map { it.toDomain() }, result.page.toDomain()))
			}
			is Result.Failure -> {
				throw ApiResultException(response.error)
			}
			is Result.NetworkError -> {
				throw response.exception
			}
			is Result.Unexpected -> {
				throw response.t ?: ApiResultException()
			}
		}
	}.flowOn(ioDispatcher)
}