package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.AuthRepository
import javax.inject.Inject

class ReissueTokenUseCase @Inject constructor(
	private val authRepository: AuthRepository
) {
	// 토큰 재발급
	suspend operator fun invoke(token: String) = authRepository.reissueToken(token)
}