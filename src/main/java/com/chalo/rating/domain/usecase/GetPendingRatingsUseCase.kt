package com.chalo.rating.domain.usecase

import com.chalo.core.model.base.ApiResult
import com.chalo.rating.data.toDomain
import com.chalo.rating.domain.model.PendingRating
import com.chalo.rating.domain.repo.RatingRepository

class GetPendingRatingsUseCase(
    private val repository: RatingRepository
) {
    suspend operator fun invoke(): ApiResult<PendingRating?> {

        // Delegate to repository and map the result
        return when (val result = repository.getPendingRatings()) {
            is ApiResult.Success -> {
                ApiResult.Success(result.data?.toDomain())
            }
            is ApiResult.Error -> {
                ApiResult.Error(result.message)
            }
        }
    }
}
