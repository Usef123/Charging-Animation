package com.noor.charginganimation.domain.usecase

import com.noor.charginganimation.domain.repository.AnimationRepository

class AddAnimation(
    private val repo: AnimationRepository
) {
    suspend operator fun invoke(
        name: String,
        url: String,
        category: String,
        thumbnail: String
    ) = repo.addAnimationToFireStore(name, url, category, thumbnail)
}