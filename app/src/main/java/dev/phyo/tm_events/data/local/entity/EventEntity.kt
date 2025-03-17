package dev.phyo.tm_events.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.phyo.tm_events.data.remote.model.Dates
import dev.phyo.tm_events.data.remote.model.Embedded
import dev.phyo.tm_events.data.remote.model.EventDto
import dev.phyo.tm_events.data.remote.model.ImageDto
import dev.phyo.tm_events.data.remote.model.Start
import dev.phyo.tm_events.util.getHighestResolutionImage

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String,
    val imageUrl: String?=null,
    val imageRatio: String ?= null,
    val imageWidth: Int? = null,
    val imageHeight: Int? = null,
    val eventDate: String? = null,
    val eventTime: String? = null,
    val venueName: String? = null
)