package dev.phyo.tm_events.data.remote.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("_embedded")
    val embedded: Embedded?,
    @SerializedName("page")
    val page: Page?
)
