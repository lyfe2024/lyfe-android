package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.AuthRepository
import javax.inject.Inject

class GetNewUserTokenUseCase @Inject constructor(
	private val authRepository: AuthRepository
) {
	// 토큰 재발급
	operator fun invoke(token: String) = authRepository.reissueToken(token)
}