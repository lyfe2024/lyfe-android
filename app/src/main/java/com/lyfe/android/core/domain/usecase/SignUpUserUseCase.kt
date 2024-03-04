package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
	private val authRepository: AuthRepository
) {
	// 회원가입
	suspend operator fun invoke(userToken: String, nickname: String) = authRepository.postUser(userToken, nickname)
}