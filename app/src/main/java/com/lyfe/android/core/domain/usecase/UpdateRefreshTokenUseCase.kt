package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.TokenRepository
import javax.inject.Inject

class UpdateRefreshTokenUseCase @Inject constructor(
	private val tokenRepository: TokenRepository
) {
	// 유저 리프레시 토큰 저장
	suspend operator fun invoke(refreshToken: String) = tokenRepository.updateRefreshToken(refreshToken)
}