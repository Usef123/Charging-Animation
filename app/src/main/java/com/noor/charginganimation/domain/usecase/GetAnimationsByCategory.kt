package com.noor.charginganimation.domain.usecase

import com.noor.charginganimation.domain.repository.AnimationRepository

class GetAnimationsByCategory (
    private val repo: AnimationRepository
) {
    operator fun invoke(category: String) = repo.getAnimationsByCategory(category)
}