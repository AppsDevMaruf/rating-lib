package com.chalo.ratting.data

import com.chalo.ratting.domain.model.PendingRating
import com.google.gson.annotations.SerializedName

data class PendingRatingDto(
    @SerializedName("pending")
    val pending: Boolean? = null,

    @SerializedName("rideId")
    val rideId: String? = null,

    @SerializedName("userId")
    val userId: String? = null,

    @SerializedName("userName")
    val userName: String? = null,

    @SerializedName("userPhoto")
    val userPhoto: String? = null,

    @SerializedName("rating")
    val rating: Double? = null,

    @SerializedName("skip")
    val skip: Boolean? = null
)



fun PendingRatingDto.toDomain(): PendingRating {
    return PendingRating(
        isPending = this.pending ?: false,
        rideId = this.rideId.orEmpty(),
        userId = this.userId.orEmpty(),
        userName = this.userName,
        userPhoto = this.userPhoto,
        rating = this.rating,
        isSkipped = this.skip ?: false
    )
}
