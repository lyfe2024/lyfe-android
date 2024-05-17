package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
	private val userRepository: UserRepository
) {

	suspend operator fun invoke(
		nickname: String,
		profileUrl: String
	) = userRepository.putUserInfo(
		nickname = nickname,
		profileUrl = profileUrl
	)
}