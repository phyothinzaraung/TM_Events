package dev.phyo.tm_events.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorView(errorMessage: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
                .testTag("An error occurred")
        )

    }
}