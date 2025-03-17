package dev.phyo.tm_events.data.mapper

import dev.phyo.tm_events.data.local.entity.EventEntity
import dev.phyo.tm_events.data.remote.model.EventDto
import dev.phyo.tm_events.data.remote.model.ImageDto
import dev.phyo.tm_events.domain.model.Event

fun EventEntity.toDomain(): Event {
    return Event(
        id = id,
        name = name,
        imageUrl = imageUrl ?: "",
        eventDate = eventDate ?: "",
        eventTime = eventTime ?: "",
        venueName = venueName ?: ""
    )
}

fun EventDto.toEntity(): EventEntity {
    val bestImage = imageDtos.maxByOrNull { it.width * it.height } ?: ImageDto("", "",0, 0)
    return EventEntity(
        name = name,
        imageUrl = bestImage.url ?: "",
        imageRatio = bestImage.ratio ?: "",
        imageWidth = bestImage.width ?: 0,
        imageHeight = bestImage.height ?: 0,
        eventDate = dates.start.localDate,
        eventTime = dates.start.localTime,
        venueName = embedded.venues.firstOrNull()?.name ?: "unknown"
    )
}