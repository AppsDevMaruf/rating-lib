package com.chalo.rating.domain.repo

import com.chalo.core.model.base.ApiResult
import com.chalo.core.model.base.SimpleResponse
import com.chalo.rating.data.BehaviourItemDto
import com.chalo.rating.data.PendingRatingDto
import com.chalo.rating.data.SubmitRatingRequestDto

interface RatingRepository {
    suspend fun getPendingRatings(): ApiResult<PendingRatingDto?>
    suspend fun getDriverBehaviors(): ApiResult<Map<String, List<BehaviourItemDto>>?>
    suspend fun submitRating(request: SubmitRatingRequestDto): ApiResult<SimpleResponse?>
}
