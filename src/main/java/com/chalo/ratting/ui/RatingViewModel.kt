package com.chalo.ratting.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chalo.core.model.base.ApiResult
import com.chalo.core.model.base.SimpleResponse
import com.chalo.ratting.data.SubmitRatingRequestDto
import com.chalo.ratting.domain.model.Behaviour
import com.chalo.ratting.domain.model.PendingRating
import com.chalo.ratting.domain.usecase.GetBehaviorsUseCase
import com.chalo.ratting.domain.usecase.GetPendingRatingsUseCase
import com.chalo.ratting.domain.usecase.SubmitRatingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RatingViewModel(
    private val getPendingRatingsUseCase: GetPendingRatingsUseCase,
    private val getDriverBehaviorsByRatingUseCase: GetBehaviorsUseCase,
    private val submitRatingUseCase: SubmitRatingUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RattingUiState())
    val uiState: StateFlow<RattingUiState> = _uiState.asStateFlow()

    fun checkPendingRating() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = getPendingRatingsUseCase()) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            pendingRating = result.data,
                            hasPendingRating = result.data?.isPending,
                            errorMessage = null
                        )
                    }
                }

                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun getDriverBehaviors() {
        viewModelScope.launch {
            // Don't show loading for behavior loading to avoid UI flickering
            when (val result = getDriverBehaviorsByRatingUseCase()) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            behaviorsByRating = result.data,
                            errorMessage = null
                        )
                    }
                }

                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun submitRating(requestDto: SubmitRatingRequestDto) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = submitRatingUseCase(requestDto)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            submitRating = result.data,
                            hasPendingRating = false, // Clear pending rating after successful submission
                            pendingRating = null,     // Clear pending rating data
                            errorMessage = null
                        )
                    }
                }

                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun dismissRating() {
        _uiState.update {
            it.copy(
                hasPendingRating = false,
                pendingRating = null,
                behaviorsByRating = null,
                errorMessage = null
            )
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }

    fun resetState() {
        _uiState.value = RattingUiState()
    }

    override fun onCleared() {
        super.onCleared()
        // Clean up any resources if needed
    }
}

data class RattingUiState(
    val hasPendingRating: Boolean? = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pendingRating: PendingRating? = null,
    val behaviorsByRating: Map<Int, List<Behaviour>>? = null,
    val submitRating: SimpleResponse? = null,
) {
    val isSuccess: Boolean get() = pendingRating != null && errorMessage == null
    val isError: Boolean get() = errorMessage != null
    val hasDriverBehaviors: Boolean get() = !behaviorsByRating.isNullOrEmpty()
}
