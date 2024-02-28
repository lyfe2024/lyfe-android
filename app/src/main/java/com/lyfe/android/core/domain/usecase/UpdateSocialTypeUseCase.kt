package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.TokenRepository
import javax.inject.Inject

class UpdateSocialTypeUseCase @Inject constructor(
	private val tokenRepository: TokenRepository
) {
	// 유저 액세스 토큰 저장
	suspend operator fun invoke(socialType: String) = tokenRepository.updateSocialType(socialType)
}