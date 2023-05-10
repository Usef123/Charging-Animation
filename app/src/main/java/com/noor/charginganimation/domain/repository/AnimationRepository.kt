package com.noor.charginganimation.domain.repository

import com.noor.charginganimation.domain.model.Animation
import com.noor.charginganimation.domain.model.Response
import kotlinx.coroutines.flow.Flow

typealias Animations = List<Animation>
typealias AnimationResponse = Response<Animations>
typealias AddAnimationResponse = Response<Boolean>
typealias DeleteAnimationResponse = Response<Boolean>

interface AnimationRepository {

    fun getAnimationsFromFireStore(): Flow<AnimationResponse>

    fun getAnimationsByCategoryResponse(category: String): Flow<AnimationResponse>

    suspend fun getAnimationsForSpecificCategory(category: String): AnimationResponse

    fun getAnimationsByCategory(category: String): Flow<List<Animation>>

    suspend fun addAnimationToFireStore(name: String, url: String, category: String, thumbnail: String): AddAnimationResponse

    suspend fun deleteAnimationFromFireStore(animId: String): DeleteAnimationResponse
}