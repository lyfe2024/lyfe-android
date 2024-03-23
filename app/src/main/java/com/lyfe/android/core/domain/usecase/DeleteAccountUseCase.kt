package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
	private val authRepository: AuthRepository
) {

	suspend operator fun invoke() = authRepository.revoke()
}