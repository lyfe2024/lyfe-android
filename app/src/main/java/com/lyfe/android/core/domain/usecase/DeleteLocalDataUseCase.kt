package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.TokenRepository
import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class DeleteLocalDataUseCase @Inject constructor(
	val tokenRepository: TokenRepository,
	val userRepository: UserRepository
) {
	// 소셜 로그인
	suspend operator fun invoke() {
		tokenRepository.deleteAllToken()
		userRepository.deleteAllLocalUserData()
	}
}