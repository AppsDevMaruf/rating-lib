package com.chalo.rating.domain.usecase

import com.chalo.core.model.base.ApiResult
import com.chalo.core.model.base.SimpleResponse
import com.chalo.rating.data.SubmitRatingRequestDto
import com.chalo.rating.domain.repo.RatingRepository

class SubmitRatingUseCase(
    private val repository: RatingRepository
) {
    suspend operator fun invoke(request: SubmitRatingRequestDto): ApiResult<SimpleResponse?> {
        // Delegate to repository and map the result
        return when (val result = repository.submitRating(request)) {
            is ApiResult.Success -> {
                ApiResult.Success(SimpleResponse(title = "${result.data?.title}", detail = "${result.data?.detail}"))
            }
            is ApiResult.Error -> {
                ApiResult.Error(result.message)
            }
        }
    }
}
