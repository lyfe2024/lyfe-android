package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class EditUserInfoUseCase @Inject constructor(
	private val repository: UserRepository
) {

	suspend operator fun invoke(
		nickname: String,
		profileUrl: String,
		width: Int,
		height: Int
	) = repository.putUserInfo(nickname, profileUrl, width, height)
}