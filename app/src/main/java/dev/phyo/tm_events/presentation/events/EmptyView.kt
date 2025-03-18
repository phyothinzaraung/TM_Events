package dev.phyo.tm_events.presentation.events

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyView(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "No Data",
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )

    }
}