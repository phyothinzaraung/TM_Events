package dev.phyo.tm_events.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.phyo.tm_events.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyView(
    onRefresh:() -> Unit,
    modifier: Modifier = Modifier) {

    var isRefreshing by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.toolbar_text)) })
    }){ innerPadding ->
            Box(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.no_data_text),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        isRefreshing = true
                        onRefresh()
                    }) {
                        Text(text = stringResource(R.string.refresh_text))
                    }
                }
            }
    }
}