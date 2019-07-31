package com.example.android.currencyconverter.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A retrofit service to fetch a current rates.
 */
interface FixerService {
    @GET("api/latest")
    fun getCurrentExchangeRates(
        @Query("access_key") key: String = "cf1c7056a8d133899f6e6a9e29d9c2df",
        @Query("symbols") symbols: String = "USD,NGN,AED,GBP,JPY,CNY",
        @Query("format") format: Int = 1
    ): Call<Response>


}


/**
 * Main entry point for network access. Call like `Network.fixerIO.getCurrentExchangeRates()`
 */
object Network {
    // Configure retrofit to parse JSON
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://data.fixer.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val fixerIO: FixerService = retrofit.create(FixerService::class.java)
}


