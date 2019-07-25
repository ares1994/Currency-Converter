package com.example.android.currencyconverter

import android.util.Log
import com.example.android.currencyconverter.home.DatabaseRates
import com.example.android.currencyconverter.network.Network
import com.example.android.currencyconverter.network.Rates
import com.example.android.currencyconverter.network.Response
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback

class ConverterRepo {
    val realm: Realm by lazy {
        val config = RealmConfiguration.Builder()
            .name("converter.realm")
            .schemaVersion(2)
            .build()
        Realm.getInstance(config)
//    Realm.getDefaultInstance()
    }

    fun insert(rates: Rates) {

        realm.beginTransaction()

        if (realm.where(DatabaseRates::class.java).max("id") == null) {
            val databaseRates = realm.createObject(DatabaseRates::class.java, 1)
            databaseRates.uSD = rates.uSD
            databaseRates.nGN = rates.nGN
            databaseRates.jPY = rates.jPY
            databaseRates.gBP = rates.gBP
            databaseRates.cNY = rates.cNY
            databaseRates.aED = rates.aED
            databaseRates.timeEntered = System.currentTimeMillis()
            realm.commitTransaction()
        } else {
            val databaseRates = find()
            databaseRates!!.uSD = rates.uSD
            databaseRates.nGN = rates.nGN
            databaseRates.jPY = rates.jPY
            databaseRates.gBP = rates.gBP
            databaseRates.cNY = rates.cNY
            databaseRates.aED = rates.aED
            databaseRates.timeEntered = System.currentTimeMillis()

            realm.commitTransaction()
        }


        Log.w("Ares", "Insert successfully called")

    }

    private fun find(): DatabaseRates? {
        val id = 1
        return realm.where(DatabaseRates::class.java).equalTo("id", id).findFirst()
    }


    fun refreshCurrencyRates() {
        Network.fixerIO.getCurrentExchangeRates().enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d("Ares", "${t.message}")
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                response.body()!!.rates?.let { insert(it) }

            }

        })


    }


}