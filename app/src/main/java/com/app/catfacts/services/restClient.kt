package com.app.catfacts.services

import com.app.catfacts.models.dataModels.CatsModel
import retrofit2.Response
import retrofit2.http.GET

interface RestClient {
    @GET("facts/")
    suspend fun getFacts(): Response<List<CatsModel>>
}