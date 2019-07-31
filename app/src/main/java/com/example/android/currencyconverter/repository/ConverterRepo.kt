package com.example.android.currencyconverter.repository

import android.util.Log
import com.example.android.currencyconverter.DatabaseRates
import com.example.android.currencyconverter.network.Network
import com.example.android.currencyconverter.network.Response
import io.realm.Realm
import io.realm.RealmConfiguration
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

    //Inserts data passed to it into realm database
    fun insert(response: Response) {

        realm.beginTransaction()

        if (realm.where(DatabaseRates::class.java).max("id") == null) {
            val databaseRates = realm.createObject(DatabaseRates::class.java, 1)
            databaseRates.uSD = response.rates?.uSD
            databaseRates.nGN = response.rates?.nGN
            databaseRates.jPY = response.rates?.jPY
            databaseRates.gBP = response.rates?.gBP
            databaseRates.cNY = response.rates?.cNY
            databaseRates.aED = response.rates?.aED
            databaseRates.timeEntered = response.timestamp?.toLong()
            realm.commitTransaction()
        } else {
            val databaseRates = realm.find()
            databaseRates!!.uSD = response.rates?.uSD
            databaseRates.nGN = response.rates?.nGN
            databaseRates.jPY = response.rates?.jPY
            databaseRates.gBP = response.rates?.gBP
            databaseRates.cNY = response.rates?.cNY
            databaseRates.aED = response.rates?.aED
            databaseRates.timeEntered = System.currentTimeMillis()
            realm.commitTransaction()
        }
        Log.w("Ares", "Insert successfully called")
    }

//Retrieves data from Fixer.io and inserts into Realm database
    fun refreshCurrencyRates() {
        Network.fixerIO.getCurrentExchangeRates().enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d("Ares", "${t.message}")
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                insert(response.body()!!)
            }
        })
    }




}
//Finds available currency rates from realm database
fun Realm.find(): DatabaseRates? {
    val id = 1
    return this.where(DatabaseRates::class.java).equalTo("id", id).findFirst()
}