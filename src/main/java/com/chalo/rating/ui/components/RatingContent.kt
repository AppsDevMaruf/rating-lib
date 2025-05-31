package com.chalo.rating.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chalo.rating.domain.model.PendingRating

@Composable
fun RatingContent(
    rating: Int,
    feedback: String,
    selectedBehaviors: List<String>,
    isSubmitting: Boolean,
    pendingRating: PendingRating?,

    onRatingChanged: (Int) -> Unit,
    onFeedbackChanged: (String) -> Unit,
    onBehaviorsChanged: (List<String>) -> Unit,
    onSubmit: () -> Unit,
    onSkip: () -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header Section
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "How was your trip?",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = pendingRating?.userName?.let { "Please rate your experience with $it" }
                        ?: "Please rate your experience",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Rating Stars Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RatingBar(
                        rating = rating,
                        onRatingChanged = onRatingChanged,
                    )

                }
            }
        }


        item {

        }


        // Action Buttons
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onSubmit,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSubmitting,
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    if (isSubmitting) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text("Submitting...")
                        }
                    } else {
                        Text(
                            text = "Submit Rating",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }

                OutlinedButton(
                    onClick = onSkip,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSubmitting,
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Text(
                        text = "Skip for now",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }

        // Bottom Spacing
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultiSelectableChips(
    items: List<Pair<String, String>>, // id to title
    selectedIds: Set<String>,
    onSelectionChanged: (Set<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = Int.MAX_VALUE, // can be adjusted as needed
        maxLines = Int.MAX_VALUE,
        overflow = FlowRowOverflow.Clip
    ) {
        items.forEach { (id, title) ->
            val isSelected = selectedIds.contains(id)

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) Color.Black else Color.Transparent)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color.Black else Color.Gray,
                        shape = RoundedCornerShape(50)
                    )
                    .clickable {
                        val newSet = selectedIds.toMutableSet()
                        if (isSelected) newSet.remove(id) else newSet.add(id)
                        onSelectionChanged(newSet)
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = title,
                    color = if (isSelected) Color.White else Color.Black,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                )
            }

        }
    }
}
