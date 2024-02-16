package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.TokenRepository
import javax.inject.Inject

class DeleteSignUpTokenUseCase @Inject constructor(
	private val tokenRepository: TokenRepository
) {

	suspend operator fun invoke() = tokenRepository.deleteSignUpToken()
}