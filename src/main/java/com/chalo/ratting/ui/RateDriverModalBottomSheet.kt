package com.chalo.ratting.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chalo.ratting.R
import com.chalo.ratting.ui.components.ErrorContent
import com.chalo.ratting.ui.components.LoadingContent
import org.koin.androidx.compose.koinViewModel

// Rating Bottom Sheet UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateDriverModalBottomSheet(
    sheetState: SheetState,
    uiState: RattingUiState,
    onDismiss: () -> Unit,
    onRetry: () -> Unit,
) {
    val ratingViewModel: RatingViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        ratingViewModel.getDriverBehaviors()
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(com.chalo.core.R.drawable.ic_bottom_header),
                    contentDescription = "Drag Handle",
                    tint = Color.Unspecified,
                )
            }
        },
        contentWindowInsets = { WindowInsets(0) },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
        ) {
            when {
                uiState.isLoading && uiState.pendingRating == null -> {
                    LoadingContent()
                }

                uiState.isError -> {
                    ErrorContent(
                        message = uiState.errorMessage ?: "Something went wrong",
                        onRetry = onRetry,
                        onDismiss = onDismiss
                    )
                }

                else -> {
                    RateDriverScreen(
                        pendingRatingInfo = uiState.pendingRating,
                        behaviorsByRating = uiState.behaviorsByRating.orEmpty(),
                        onSubmit = { request ->
                            ratingViewModel.submitRating(request)
                        }
                    )

                }
            }
        }
    }
}
