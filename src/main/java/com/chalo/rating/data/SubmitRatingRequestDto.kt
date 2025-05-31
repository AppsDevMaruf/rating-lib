package com.chalo.rating.data

data class SubmitRatingRequestDto(
    val rideId: String,
    val rating: Int,
    val comment: String? = null,
    val skip: Boolean,
    val ratingBehaviorIds: List<String?>? = null
)
