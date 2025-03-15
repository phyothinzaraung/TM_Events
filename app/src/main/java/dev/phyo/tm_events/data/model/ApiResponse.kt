package dev.phyo.tm_events.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("_embedded") val embedded: Embedded?,
    @SerializedName("_links") val links: Links?,
    @SerializedName("page") val page: Page?
)
