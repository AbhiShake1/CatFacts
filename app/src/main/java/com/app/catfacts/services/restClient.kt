package com.app.catfacts.services

import com.app.catfacts.models.dataModels.CatsModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

public interface RestClient {
    // retrofit reference: https://github.com/square/retrofit
    @GET("facts/random/")
    suspend fun getFacts(@Query("amount") amount: Int = 5): Response<List<CatsModel>>
}

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://cat-fact.herokuapp.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

public val apiService = retrofit.create(RestClient::class.java)
