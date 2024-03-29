package com.noor.charginganimation.domain.usecase

data class UseCases (
    val getAnimations: GetAnimations,
    val getAnimationsByCategory: GetAnimationsByCategory,
    val getAnimationsForSpecificCategory: GetAnimationsForSpecificCategory,
    val addAnimation: AddAnimation,
    val deleteAnimation: DeleteAnimation
)