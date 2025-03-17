package dev.phyo.tm_events.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import dev.phyo.tm_events.data.local.entity.EventEntity
import dev.phyo.tm_events.data.remote.model.EventDto
import dev.phyo.tm_events.data.remote.model.ImageDto
import dev.phyo.tm_events.domain.model.Event
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun EventEntity.toDomain(): Event {
    return Event(
        id = id,
        name = name,
        imageUrl = imageUrl ?: "",
        eventDate = eventDate ?: "",
        eventTime = eventTime ?: "",
        venueName = venueName ?: "",
        city = city ?: "",
        stateCode = stateCode ?: ""
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun EventDto.toEntity(): EventEntity {
    val bestImage = imageDtos.maxByOrNull { it.width * it.height } ?: ImageDto("", "",0, 0)

    val eventDate = LocalDate.parse(dates.start.localDate, DateTimeFormatter.ISO_LOCAL_DATE)
    val formattedDate = eventDate.format(DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH))

    return EventEntity(
        name = name,
        imageUrl = bestImage.url ?: "",
        imageRatio = bestImage.ratio ?: "",
        imageWidth = bestImage.width ?: 0,
        imageHeight = bestImage.height ?: 0,
        eventDate = formattedDate ?: "",
        eventTime = dates.start.localTime ?: "",
        venueName = embedded.venues.firstOrNull()?.name ?: "unknown",
        city = embedded.venues.firstOrNull()?.city?.name ?: "",
        stateCode = embedded.venues.firstOrNull()?.state?.stateCode ?: ""
    )
}