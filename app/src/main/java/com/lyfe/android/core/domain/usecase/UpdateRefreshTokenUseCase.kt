package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class UpdateRefreshTokenUseCase @Inject constructor(
	private val userRepository: UserRepository
) {
	// 유저 리프레시 토큰 저장
	suspend operator fun invoke(refreshToken: String) = userRepository.updateRefreshToken(refreshToken)
}