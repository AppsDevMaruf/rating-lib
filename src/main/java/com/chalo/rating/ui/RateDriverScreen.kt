package com.chalo.rating.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chalo.core.components.DividerSection
import com.chalo.core.components.LoadImage
import com.chalo.core.theme.buttonBgPrimary
import com.chalo.core.theme.buttonBgSecondary
import com.chalo.core.theme.colorDarkText
import com.chalo.core.theme.colorLightText
import com.chalo.core.theme.headingTextStyle
import com.chalo.core.theme.titleTextStyle
import com.chalo.rating.data.SubmitRatingRequestDto
import com.chalo.rating.domain.model.Behaviour
import com.chalo.rating.domain.model.PendingRating
import com.chalo.rating.ui.components.RatingBar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RateDriverScreen(
    behaviorsByRating: Map<Int, List<Behaviour>>,
    onSubmit: (SubmitRatingRequestDto) -> Unit,
    pendingRatingInfo: PendingRating?
) {
    var selectedRating by remember { mutableIntStateOf(5) }
    var selectedBehaviors by remember { mutableStateOf(setOf<String?>()) }
    var comment by remember { mutableStateOf("") }

    val behaviors = behaviorsByRating[selectedRating] ?: emptyList()
    LaunchedEffect(selectedRating) {
        selectedBehaviors = emptySet()
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .wrapContentHeight()

    ) {
        // Title
        Text(
            "Your trip has ended!",
            style = headingTextStyle,
            fontSize = 24.sp,
            textAlign = TextAlign.Center

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "How was the trip?",
            style = headingTextStyle,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        DividerSection(modifier = Modifier.padding(vertical = 12.dp))

        // Driver Name & Rating
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                pendingRatingInfo?.userName?.let {
                    Text(
                        text = it,
                        style = headingTextStyle,
                        maxLines = 1,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                LoadImage(
                    imageUrl = pendingRatingInfo?.userPhoto,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(8.dp))
                RatingBar(selectedRating) { selectedRating = it }
                Spacer(modifier = Modifier.height(8.dp))
                // Behavior Tags
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy((-10).dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    behaviors.forEach { behavior ->
                        val isSelected = selectedBehaviors.contains(behavior.id)

                        FilterChip(
                            selected = isSelected,
                            modifier = Modifier.defaultMinSize(minHeight = 24.dp),
                            onClick = {
                                selectedBehaviors = if (isSelected) {
                                    selectedBehaviors - behavior.id
                                } else {
                                    selectedBehaviors + behavior.id
                                }
                            },
                            label = {
                                behavior.displayTitle?.let {
                                    Text(
                                        it,
                                        style = titleTextStyle,
                                        fontSize = 12.sp,
                                        color = if (isSelected) colorDarkText else colorLightText
                                    )
                                }
                            },
                            shape = RoundedCornerShape(24.dp),
                            enabled = true,
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color.Transparent,
                                selectedContainerColor = Color.Black,
                                labelColor = Color.Black,
                                selectedLabelColor = Color.White,
                                disabledLabelColor = Color.Gray
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = Color.Black,
                                selectedBorderColor = Color.Black,
                                borderWidth = 1.dp,
                                selected = isSelected,
                                enabled = true
                            )
                        )
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Description
        /* OutlinedTextField(
             value = comment,
             onValueChange = { comment = it },
             modifier = Modifier
                 .fillMaxWidth()
                 .height(120.dp),
             placeholder = { Text("Description") }
         )*/
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Skip Button
            Button(
                onClick = {
                    onSubmit(
                        SubmitRatingRequestDto(
                            pendingRatingInfo?.rideId.orEmpty(),
                            selectedRating,
                            null,
                            true,
                            null
                        )
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBgSecondary,

                    ),
                shape = RoundedCornerShape(8.dp),

                ) {
                Text(
                    text = "Skip",
                    fontSize = 16.sp,
                    style = titleTextStyle,
                    color = colorLightText
                )
            }

            // Rate Driver Button
            Button(
                onClick = {
                    onSubmit(
                        SubmitRatingRequestDto(
                            rideId = pendingRatingInfo?.rideId.orEmpty(),
                            rating = selectedRating,
                            comment = comment.takeIf { it.isNotBlank() },
                            skip = false,
                            ratingBehaviorIds = selectedBehaviors.toList()
                        )
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBgPrimary,
                ),
                shape = RoundedCornerShape(8.dp),

                ) {
                Text(
                    text = "Rate Driver",
                    fontSize = 16.sp,
                    style = titleTextStyle,
                    color = colorDarkText
                )
            }
        }
        Spacer(Modifier.height(24.dp))

    }
}
