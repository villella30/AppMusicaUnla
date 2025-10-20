package com.example.appmusicagrupon

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApiService {

    @GET("/playlist/1313621735")
    suspend fun getTracks(
        @Query("limit") limit: Int = 100
    ): Response<DeezerResponse>
}