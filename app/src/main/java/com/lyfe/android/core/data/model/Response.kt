package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

interface Response<T : Any> {
	@Serializable
	var result: T
}