package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class GetSocialTypeUseCase @Inject constructor(
	private val userRepository: UserRepository
) {

	operator fun invoke() = userRepository.getSocialType()
}