package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class DeleteSignUpTokenUseCase @Inject constructor(
	private val userRepository: UserRepository
) {

	suspend operator fun invoke() = userRepository.deleteSignUpToken()
}