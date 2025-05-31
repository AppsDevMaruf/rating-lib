package com.chalo.ratting.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chalo.ratting.data.SubmitRatingRequestDto
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber


// Main Rating Sheet Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowRatingSheet() {
    val ratingViewModel: RatingViewModel = koinViewModel()
    val uiState by ratingViewModel.uiState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    // Handle state changes - show sheet when there's pending rating
    LaunchedEffect(uiState.hasPendingRating) {
        if (uiState.hasPendingRating == true) {
            showSheet = true
        }
    }

    // Check for pending rating on first composition
    LaunchedEffect(Unit) {
        ratingViewModel.checkPendingRating()
    }

    // Handle successful submission - hide sheet when rating is submitted
    LaunchedEffect(uiState.submitRating) {
        if (uiState.submitRating != null) {
            showSheet = false
            // Optional: Show success toast/snackbar here
        }
    }

    Timber.tag("RatingSheet")
        .d("showSheet: $showSheet, hasPendingRating: ${uiState.hasPendingRating}")

    // Show the bottom sheet
    if (showSheet) {
        RateDriverModalBottomSheet(
            sheetState = sheetState,
            uiState = uiState,
            onDismiss = {
                showSheet = false
                ratingViewModel.dismissRating()
            },
            onRetry = {
                ratingViewModel.clearError()
                ratingViewModel.checkPendingRating()
            },
        )
    }
}
