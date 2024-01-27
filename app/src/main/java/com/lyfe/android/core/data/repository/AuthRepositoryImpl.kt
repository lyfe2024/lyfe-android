package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.AuthDataSource
import com.lyfe.android.core.data.model.AuthUserResponse
import com.lyfe.android.core.data.model.PostUserResponse
import com.lyfe.android.core.data.model.ReissueTokenResponse
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val authDataSource: AuthDataSource,
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

	override fun postUser(userToken: String, nickname: String): Flow<Result<PostUserResponse>> = flow {
		emit(authDataSource.postUser(userToken, nickname))
	}.flowOn(ioDispatcher)

	override fun authUser(
		socialType: String,
		authorizationCode: String,
		identityToken: String,
		fcmToken: String
	): Flow<Result<AuthUserResponse>> = flow {
		emit(authDataSource.authUser(socialType, authorizationCode, identityToken, fcmToken))
	}.flowOn(ioDispatcher)

	override fun reissueToken(token: String): Flow<Result<ReissueTokenResponse>> = flow {
		emit(authDataSource.reissueToken(token))
	}.flowOn(ioDispatcher)
}