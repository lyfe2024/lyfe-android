package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.GoogleDataSource
import com.lyfe.android.core.data.model.GoogleTokenRequest
import com.lyfe.android.core.data.model.GoogleTokenResponse
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.GoogleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GoogleRepositoryImpl @Inject constructor(
	private val googleDataSource: GoogleDataSource,
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): GoogleRepository {

	override suspend fun getAccessToken(request: GoogleTokenRequest): Flow<Result<GoogleTokenResponse>> = flow {
		emit(googleDataSource.getAccessToken(request))
	}.flowOn(ioDispatcher)
}