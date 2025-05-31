package com.chalo.ratting.data

import androidx.annotation.Keep
import com.chalo.ratting.domain.model.Behaviour
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
