package com.noor.charginganimation.presentation.fragments.animations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noor.charginganimation.core.Constants.ABSTRACT
import com.noor.charginganimation.core.Constants.CARTOON
import com.noor.charginganimation.core.Constants.CHARGING
import com.noor.charginganimation.core.Constants.HEART
import com.noor.charginganimation.domain.model.Animation
import com.noor.charginganimation.domain.model.Response
import com.noor.charginganimation.domain.repository.AddAnimationResponse
import com.noor.charginganimation.domain.repository.AnimationResponse
import com.noor.charginganimation.domain.repository.DeleteAnimationResponse
import com.noor.charginganimation.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimationsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private var _animationsResponse = MutableStateFlow<AnimationResponse>(Response.Loading)
    val animationsResponse = _animationsResponse.asStateFlow()

    private var _animationsByCategoryResponse = MutableStateFlow<AnimationResponse>(Response.Loading)
    val animationsByCategoryResponse = _animationsByCategoryResponse.asStateFlow()

    private val _abstractAnimationsResponse = MutableLiveData<AnimationResponse>()
    val abstractAnimationsResponse: LiveData<AnimationResponse> = _abstractAnimationsResponse

    private val _cartoonAnimationsResponse = MutableLiveData<AnimationResponse>()
    val cartoonAnimationsResponse: LiveData<AnimationResponse> = _cartoonAnimationsResponse

    private val _heartAnimationsResponse = MutableLiveData<AnimationResponse>()
    val heartAnimationsResponse: LiveData<AnimationResponse> = _heartAnimationsResponse

    private val _chargingAnimationsResponse = MutableLiveData<AnimationResponse>()
    val chargingAnimationsResponse: LiveData<AnimationResponse> = _chargingAnimationsResponse

    var addAnimationResponse: AddAnimationResponse = Response.Success(false)
        private set

    var deleteAnimationResponse: DeleteAnimationResponse = Response.Success(false)
        private set


    init {
//        getAnimations()

        getAnimationsTesting()
    }

    fun getAnimationsByCategory(category: String): Flow<AnimationResponse> {
        viewModelScope.launch {
            useCases.getAnimationsByCategory(category).collect { response ->
                _animationsByCategoryResponse.value = response
            }
        }
        return _animationsByCategoryResponse
    }

    private fun getAnimationsTesting() = viewModelScope.launch {
        _abstractAnimationsResponse.value = Response.Loading
        _cartoonAnimationsResponse.value = Response.Loading
        _heartAnimationsResponse.value = Response.Loading
        _chargingAnimationsResponse.value = Response.Loading

        val abstractResult = useCases.getAnimationsForSpecificCategory(ABSTRACT)
        val cartoonResult = useCases.getAnimationsForSpecificCategory(CARTOON)
        val heartResult = useCases.getAnimationsForSpecificCategory(HEART)
        val chargingResult = useCases.getAnimationsForSpecificCategory(CHARGING)

        _abstractAnimationsResponse.value = abstractResult
        _cartoonAnimationsResponse.value = cartoonResult
        _heartAnimationsResponse.value = heartResult
        _chargingAnimationsResponse.value = chargingResult
    }

    private fun getAnimations() = viewModelScope.launch {
        useCases.getAnimations().collect { response ->
            _animationsResponse.value = response
        }
    }

    fun addAnimation(name: String, url: String, category: String, thumbnail: String) =
        viewModelScope.launch {
            addAnimationResponse = Response.Loading
            addAnimationResponse = useCases.addAnimation(name, url, category, thumbnail)
        }

    fun deleteAnimation(animId: String) = viewModelScope.launch {
        deleteAnimationResponse = Response.Loading
        deleteAnimationResponse = useCases.deleteAnimation(animId)
    }
}