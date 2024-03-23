package com.lyfe.android.core.data.mapper

import com.lyfe.android.core.data.model.UserInfo
import com.lyfe.android.core.model.User

internal fun UserInfo.toDomain(): User =
	User(
		id = this.id,
		name = this.username,
		profileImage = this.profile
	)