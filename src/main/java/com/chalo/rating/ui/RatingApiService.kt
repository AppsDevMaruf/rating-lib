package com.chalo.rating.ui

import com.chalo.core.model.base.ApiResponse
import com.chalo.core.model.base.SimpleResponse
import com.chalo.rating.data.BehaviourItemDto
import com.chalo.rating.data.PendingRatingDto
import com.chalo.rating.data.SubmitRatingRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RatingApiService {
    @GET("api/rating/pending")
    suspend fun getPendingRatings(): Response<ApiResponse<PendingRatingDto>>

    @GET("api/rating/behaviours")
    suspend fun getDriverBehaviors(): Response<ApiResponse<Map<String, List<BehaviourItemDto>>?>>

    @POST("api/rating/submit")
    suspend fun submitRating(
        @Body ratingRequest: SubmitRatingRequestDto
    ):Response<ApiResponse<SimpleResponse>>
}
/* @GET("api/rating/behaviours")
    suspend fun getDriverBehaviors(): Response<ApiResponse<Map<String,List<BehaviourItemDto>>>>*/
