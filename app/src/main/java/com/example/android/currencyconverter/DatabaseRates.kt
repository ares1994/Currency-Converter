package com.example.android.currencyconverter

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass


@RealmClass
open class DatabaseRates : RealmObject(){

    @PrimaryKey
    var id : Long = 0

    var aED: Double? = null

    var jPY: Double? = null

    var gBP: Double? = null

    var uSD: Double? = null

    var cNY: Double? = null

    var nGN: Double? = null

    var timeEntered: Long? = null
}