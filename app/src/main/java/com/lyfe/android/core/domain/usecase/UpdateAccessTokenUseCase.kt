package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.TokenRepository
import javax.inject.Inject

class UpdateAccessTokenUseCase @Inject constructor(
	private val tokenRepository: TokenRepository
) {
	// 유저 액세스 토큰 저장
	suspend operator fun invoke(accessToken: String) = tokenRepository.updateAccessToken(accessToken)
}