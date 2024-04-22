package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.TokenRepository
import javax.inject.Inject

class GetRefreshTokenUseCase @Inject constructor(
	private val tokenRepository: TokenRepository
) {

	operator fun invoke() = tokenRepository.getRefreshToken()
}