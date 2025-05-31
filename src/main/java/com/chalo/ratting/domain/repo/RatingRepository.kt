package com.chalo.ratting.domain.repo

import com.chalo.core.model.base.ApiResult
import com.chalo.core.model.base.SimpleResponse
import com.chalo.ratting.data.BehaviourItemDto
import com.chalo.ratting.data.PendingRatingDto
import com.chalo.ratting.data.SubmitRatingRequestDto

interface RatingRepository {
    suspend fun getPendingRatings(): ApiResult<PendingRatingDto?>
    suspend fun getDriverBehaviors(): ApiResult<Map<String, List<BehaviourItemDto>>?>
    suspend fun submitRating(request: SubmitRatingRequestDto): ApiResult<SimpleResponse?>
}
