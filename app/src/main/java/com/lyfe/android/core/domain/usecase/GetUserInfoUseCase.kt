package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
	private val repository: UserRepository
) {

	operator fun invoke() = repository.getUserInfo()
}