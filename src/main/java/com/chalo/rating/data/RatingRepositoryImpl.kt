package com.chalo.rating.data

import com.chalo.core.model.base.ApiResult
import com.chalo.core.model.base.SimpleResponse
import com.chalo.core.model.base.safeApiCall
import com.chalo.rating.domain.repo.RatingRepository
import com.chalo.rating.ui.RatingApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class RatingRepositoryImpl(
    private val apiService: RatingApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RatingRepository {

    override suspend fun getPendingRatings(): ApiResult<PendingRatingDto?> {
      return dispatcher.safeApiCall {
          apiService.getPendingRatings()
      }
    }

    override suspend fun getDriverBehaviors(): ApiResult<Map<String, List<BehaviourItemDto>>?> {
        return dispatcher.safeApiCall {
            apiService.getDriverBehaviors()
        }
    }


    override suspend fun submitRating(request: SubmitRatingRequestDto): ApiResult<SimpleResponse?> {
        return dispatcher.safeApiCall {
            apiService.submitRating(request)
        }
    }
}
