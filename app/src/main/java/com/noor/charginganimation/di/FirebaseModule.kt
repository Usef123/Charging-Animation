package com.noor.charginganimation.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.noor.charginganimation.core.FirebaseConstants.ANIMATIONS
import com.noor.charginganimation.data.repository.AnimationRepositoryImp
import com.noor.charginganimation.domain.repository.AnimationRepository
import com.noor.charginganimation.domain.usecase.AddAnimation
import com.noor.charginganimation.domain.usecase.DeleteAnimation
import com.noor.charginganimation.domain.usecase.GetAnimations
import com.noor.charginganimation.domain.usecase.GetAnimationsByCategory
import com.noor.charginganimation.domain.usecase.GetAnimationsForSpecificCategory
import com.noor.charginganimation.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideAnimationsRef() = Firebase.firestore.collection(ANIMATIONS)

    @Provides
    fun provideBooksRepository(
        booksRef: CollectionReference
    ): AnimationRepository = AnimationRepositoryImp(booksRef)

    @Provides
    fun provideUseCases(
        repo: AnimationRepository
    ) = UseCases(
        getAnimations = GetAnimations(repo),
        getAnimationsByCategory = GetAnimationsByCategory(repo),
        getAnimationsForSpecificCategory = GetAnimationsForSpecificCategory(repo),
        addAnimation = AddAnimation(repo),
        deleteAnimation = DeleteAnimation(repo)
    )
}