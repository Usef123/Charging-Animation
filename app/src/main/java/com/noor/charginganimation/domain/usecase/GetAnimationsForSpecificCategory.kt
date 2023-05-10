package com.noor.charginganimation.domain.usecase

import com.noor.charginganimation.domain.repository.AnimationRepository

class GetAnimationsForSpecificCategory (
    private val repo: AnimationRepository
) {
    suspend operator fun invoke(category: String) = repo.getAnimationsForSpecificCategory(category)
}