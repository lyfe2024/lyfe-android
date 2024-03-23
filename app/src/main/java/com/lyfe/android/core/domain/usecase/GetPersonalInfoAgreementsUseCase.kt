package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.PolicyRepository
import javax.inject.Inject

class GetPersonalInfoAgreementsUseCase @Inject constructor(
	private val policyRepository: PolicyRepository
) {

	suspend operator fun invoke() = policyRepository.getPersonalInfoAgreements()
}