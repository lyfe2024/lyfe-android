package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.UserLocalDataSource
import com.lyfe.android.core.data.datasource.UserRemoteDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.ApiResultException
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.UserRepository
import com.lyfe.android.core.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
	private val userRemoteDataSource: UserRemoteDataSource,
	private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
	override suspend fun updateSocialType(socialType: String) {
		userLocalDataSource.updateSocialType(socialType = socialType)
	}

	override fun getSocialType(): Flow<String> {
		return userLocalDataSource.getSocialType()
	}

	override fun getUserInfo(): Flow<User> = flow {
		when (val response = userRemoteDataSource.getUserInfo()) {
			is Result.Success -> {
				val body = response.body
				emit(body.toDomain())
			}
			is Result.Failure -> {
				throw ApiResultException(response.error)
			}
			is Result.NetworkError -> {
				throw response.exception
			}
			is Result.Unexpected -> {
				throw response.t
			}
		}
	}.flowOn(ioDispatcher)

	override suspend fun fetchIsNicknameDuplicated(nickname: String) = flow {
		val result = userRemoteDataSource.checkNicknameDuplicated(nickname = nickname)
		if (result is Result.Success) {
			emit(result.body.isAvailable)
		}
	}

	override suspend fun putUserInfo(
		nickname: String,
		profileUrl: String
	) = flow {
		when (
			val response = userRemoteDataSource.putUserInfo(
				nickname = nickname,
				profileUrl = profileUrl
			)
		) {
			is Result.Success -> {
				val body = response.body
				emit(body.toDomain())
			}
			is Result.Failure -> {
				throw ApiResultException(response.error)
			}
			is Result.NetworkError -> {
				throw response.exception
			}
			is Result.Unexpected -> {
				throw response.t
			}
		}
	}
}