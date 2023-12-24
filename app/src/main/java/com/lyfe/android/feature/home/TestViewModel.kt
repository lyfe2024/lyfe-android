package com.lyfe.android.feature.home

import androidx.lifecycle.ViewModel
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
	private val testRepository: TestRepository
) : ViewModel() {

	suspend fun fetchTestData(): Boolean {
		return when (testRepository.fetchTestData()) {
			is Result.Success -> true
			else -> false
		}
	}
}