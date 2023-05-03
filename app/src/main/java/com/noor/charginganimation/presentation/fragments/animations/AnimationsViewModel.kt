package com.noor.charginganimation.presentation.fragments.animations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noor.charginganimation.domain.model.Response
import com.noor.charginganimation.domain.repository.AddAnimationResponse
import com.noor.charginganimation.domain.repository.AnimationRepository
import com.noor.charginganimation.domain.repository.AnimationResponse
import com.noor.charginganimation.domain.repository.DeleteAnimationResponse
import com.noor.charginganimation.domain.usecase.GetAnimationsByCategory
import com.noor.charginganimation.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimationsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private var _animationsResponse = MutableStateFlow<AnimationResponse>(Response.Loading)
    val animationsResponse = _animationsResponse.asStateFlow()

    private var _animationsByCategoryResponse = MutableStateFlow<AnimationResponse>(Response.Loading)

    var addAnimationResponse: AddAnimationResponse = Response.Success(false)
        private set

    var deleteAnimationResponse: DeleteAnimationResponse = Response.Success(false)
        private set


    init {
        getAnimations()
    }

    fun getAnimationsByCategory(category: String): Flow<AnimationResponse> {
        viewModelScope.launch {
            useCases.getAnimationsByCategory(category).collect { response ->
                _animationsByCategoryResponse.value = response
            }
        }
        return _animationsByCategoryResponse
    }

    private fun getAnimations() = viewModelScope.launch {
        useCases.getAnimations().collect { response ->
            _animationsResponse.value = response
        }
    }

    fun addAnimation(name: String, url: String, category: String) = viewModelScope.launch {
        addAnimationResponse = Response.Loading
        addAnimationResponse = useCases.addAnimation(name, url, category)
    }

    fun deleteAnimation(animId: String) = viewModelScope.launch {
        deleteAnimationResponse = Response.Loading
        deleteAnimationResponse = useCases.deleteAnimation(animId)
    }
}