package com.chalo.rating.domain.model

data class PendingRating(
    val isPending: Boolean,
    val rideId: String,
    val userId: String,
    val userName: String?,
    val userPhoto: String?,
    val rating: Double?,
    val isSkipped: Boolean
)
