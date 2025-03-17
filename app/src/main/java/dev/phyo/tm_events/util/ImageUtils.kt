package dev.phyo.tm_events.util

import dev.phyo.tm_events.data.remote.model.ImageDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getHighestResolutionImage(imageDtos: List<ImageDto>): ImageDto? {
    return withContext(Dispatchers.Default){
        imageDtos.maxByOrNull { it.width * it.height }
    }
}