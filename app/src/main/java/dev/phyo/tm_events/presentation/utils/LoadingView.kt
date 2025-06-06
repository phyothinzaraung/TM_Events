package dev.phyo.tm_events.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import dev.phyo.tm_events.R

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier =
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = modifier.testTag(stringResource(R.string.loading_test_tag))
        )
    }
}