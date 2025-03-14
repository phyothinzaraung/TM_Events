package dev.phyo.tm_events.data.service

import dev.phyo.tm_events.data.model.ApiResponse
import dev.phyo.tm_events.util.Constant.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IEventService {
    @GET("events.json")
    suspend fun getEvents(
        @Query("city") city: String
    ): Response<ApiResponse>
}