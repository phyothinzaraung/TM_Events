package dev.phyo.tm_events.data.remote

import dev.phyo.tm_events.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IEventService {
    @GET("events.json")
    suspend fun getEvents(
        @Query("city") city: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 20
    ): Response<ApiResponse>
}