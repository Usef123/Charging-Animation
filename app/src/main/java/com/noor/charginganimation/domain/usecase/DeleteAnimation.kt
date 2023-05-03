package com.noor.charginganimation.domain.usecase

import com.noor.charginganimation.domain.repository.AnimationRepository

class DeleteAnimation(
    private val repo: AnimationRepository
) {
    suspend operator fun invoke(animId: String) = repo.deleteAnimationFromFireStore(animId)
}