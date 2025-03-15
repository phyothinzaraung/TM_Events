package dev.phyo.tm_events.data.model

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("first") val first: Link?,
    @SerializedName("self") val self: Link?,
    @SerializedName("next") val next: Link?,
    @SerializedName("last") val last: Link?
)

data class Link(@SerializedName("href") val href: String?)