package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class UpdateSignUpTokenUseCase @Inject constructor(
	private val userRepository: UserRepository
) {
	// 유저 회원가입용 토큰 저장
	suspend operator fun invoke(signUpToken: String) = userRepository.updateSignUpToken(signUpToken)
}