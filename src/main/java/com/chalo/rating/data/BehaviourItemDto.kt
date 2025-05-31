package com.chalo.rating.data

import androidx.annotation.Keep
import com.chalo.rating.domain.model.Behaviour
import com.google.gson.annotations.SerializedName

@Keep
data class BehaviourItemDto(
    @SerializedName("id") val id: String?,
    @SerializedName("displayTitle") val displayTitle: String?
)

fun BehaviourItemDto.toBehavior(): Behaviour {
    return Behaviour(
        id = id,
        displayTitle = displayTitle,
    )
}
