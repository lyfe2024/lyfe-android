package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUserUseCase @Inject constructor(
	private val authRepository: AuthRepository
) {
	// 소셜 로그인
	suspend operator fun invoke(
		socialType: String,
		authorizationCode: String,
		identityToken: String,
		fcmToken: String
	) = authRepository.authUser(socialType, authorizationCode, identityToken, fcmToken)
}