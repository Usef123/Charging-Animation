package com.noor.charginganimation.domain.usecase

import com.noor.charginganimation.domain.repository.AnimationRepository

class GetAnimations (
    private val repo: AnimationRepository
) {
    operator fun invoke() = repo.getAnimationsFromFireStore()
}