package com.chalo.rating.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Helper Functions
@Composable
 fun getRatingText(rating: Int): String {
    return when (rating) {
        1 -> "Very Poor"
        2 -> "Poor"
        3 -> "Average"
        4 -> "Good"
        5 -> "Excellent"
        else -> "Rate your experience"
    }
}

@Composable
 fun getRatingColor(rating: Int): Color {
    return when (rating) {
        1, 2 -> MaterialTheme.colorScheme.error
        3 -> MaterialTheme.colorScheme.primary
        4, 5 -> Color(0xFF4CAF50)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
}
