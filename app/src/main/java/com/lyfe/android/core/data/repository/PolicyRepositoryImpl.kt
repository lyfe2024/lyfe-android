package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.PolicyService
import com.lyfe.android.core.domain.repository.PolicyRepository
import com.lyfe.android.core.model.ServicePolicyResult
import com.lyfe.android.core.model.UserInfoPolicyResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PolicyRepositoryImpl @Inject constructor(
	private val policyService: PolicyService,
	@Dispatcher(LyfeDispatchers.IO) private val dispatcher: CoroutineDispatcher
): PolicyRepository {

	override fun fetchServicePolicy(): Flow<Result<ServicePolicyResult>> = flow {
		when(val response = policyService.fetchServicePolicy()) {
			is Result.Success -> {
				val result = response.body?.result
				if (result != null)  {
					emit(Result.Success(result))
				} else {
					emit(Result.Failure(code = 500, error = "No Data"))
				}
			}
			is Result.Failure -> {
				emit(Result.Failure(code =  response.code, error = response.error))
			}
			is Result.NetworkError -> {
				emit(Result.NetworkError(exception = response.exception))
			}
			is Result.Unexpected -> {
				emit(Result.Unexpected(t =  response.t))
			}
		}
	}

	override fun fetchUserInfoPolicy(): Flow<Result<UserInfoPolicyResult>> = flow {
		when(val response = policyService.fetchUserInfoPolicy()) {
			is Result.Success -> {
				val result = response.body?.result
				if (result != null)  {
					emit(Result.Success(result))
				} else {
					emit(Result.Failure(code = 500, error = "No Data"))
				}
			}
			is Result.Failure -> {
				emit(Result.Failure(code =  response.code, error = response.error))
			}
			is Result.NetworkError -> {
				emit(Result.NetworkError(exception = response.exception))
			}
			is Result.Unexpected -> {
				emit(Result.Unexpected(t =  response.t))
			}
		}
	}
}