package com.example.android.currencyconverter.network

import android.provider.ContactsContract
import com.example.android.currencyconverter.home.DatabaseRates
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface FixerService {
    @GET("api/latest")
    fun getCurrentExchangeRates(
        @Query("access_key") key: String = "cf1c7056a8d133899f6e6a9e29d9c2df",
        @Query("symbols") symbols: String = "USD,NGN,AED,GBP,JPY,CNY",
        @Query("format") format: Int = 1
    ): Call<Response>
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */


/**
 * Main entry point for network access. Call like `Network.devbytes.getPlaylist()`
 */
object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://data.fixer.io/")
        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val fixerIO: FixerService = retrofit.create(FixerService::class.java)
}


