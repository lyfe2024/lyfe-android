package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.TokenRepository
import javax.inject.Inject

class UpdateSignUpTokenUseCase @Inject constructor(
	private val tokenRepository: TokenRepository
) {
	// 유저 회원가입용 토큰 저장
	suspend operator fun invoke(signUpToken: String) = tokenRepository.updateSignUpToken(signUpToken)
}