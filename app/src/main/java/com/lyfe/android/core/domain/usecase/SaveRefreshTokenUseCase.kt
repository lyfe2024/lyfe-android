package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class SaveRefreshTokenUseCase @Inject constructor(
	private val userRepository: UserRepository
) {
	// 유저 액세스 토큰 저장
	suspend operator fun invoke(refreshToken: String) = userRepository.saveUserRefreshToken(refreshToken)
}