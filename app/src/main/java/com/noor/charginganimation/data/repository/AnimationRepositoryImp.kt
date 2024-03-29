package com.noor.charginganimation.data.repository

import com.google.firebase.firestore.CollectionReference
import com.noor.charginganimation.core.FirebaseConstants.NAME
import com.noor.charginganimation.domain.model.Animation
import com.noor.charginganimation.domain.model.Response
import com.noor.charginganimation.domain.repository.AddAnimationResponse
import com.noor.charginganimation.domain.repository.AnimationRepository
import com.noor.charginganimation.domain.repository.AnimationResponse
import com.noor.charginganimation.domain.repository.DeleteAnimationResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AnimationRepositoryImp @Inject constructor(
    private val animationsRef: CollectionReference
): AnimationRepository {

    override fun getAnimationsFromFireStore() = callbackFlow {
        val snapshotListener = animationsRef.orderBy(NAME).addSnapshotListener { snapshot, e ->
            val animationsResponse = if (snapshot != null) {
                val animations = snapshot.toObjects(Animation::class.java)
                Response.Success(animations)
            } else {
                Response.Failure(e)
            }
            trySend(animationsResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getAnimationsByCategoryResponse(category: String): Flow<AnimationResponse> = callbackFlow {
        val snapshotListener = animationsRef.whereEqualTo("category", category).addSnapshotListener { snapshot, e ->
            val animationsResponse = if (snapshot != null) {
                val animations = snapshot.toObjects(Animation::class.java)
                Response.Success(animations)
            } else {
                Response.Failure(e)
            }
            trySend(animationsResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getAnimationsByCategory(category: String): Flow<List<Animation>> = callbackFlow {
        val snapshotListener = animationsRef.whereEqualTo("category", category).addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                val animations = snapshot.toObjects(Animation::class.java)
                trySend(animations)
            } else {
                trySend(emptyList())
            }
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun getAnimationsForSpecificCategory(category: String): AnimationResponse {
        return try {
            val snapshot = animationsRef.whereEqualTo("category", category).get().await()
            val animations = snapshot.toObjects(Animation::class.java)
            Response.Success(animations)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addAnimationToFireStore(
        name: String,
        url: String,
        category: String,
        thumbnail: String
    ): AddAnimationResponse {
        return try {
            val id = animationsRef.document().id
            val animation = Animation(
                id = id,
                name = name,
                url = url,
                category = category,
                thumbnail = thumbnail
            )
            animationsRef.document(id).set(animation).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun deleteAnimationFromFireStore(animId: String): DeleteAnimationResponse {
        return try {
            animationsRef.document(animId).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}