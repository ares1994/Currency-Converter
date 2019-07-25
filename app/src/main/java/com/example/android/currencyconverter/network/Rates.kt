package com.example.android.currencyconverter.network


import com.google.gson.annotations.SerializedName


data class Rates(

    @field:SerializedName("AED")
    val aED: Double? = null,

    @field:SerializedName("JPY")
    val jPY: Double? = null,

    @field:SerializedName("GBP")
    val gBP: Double? = null,

    @field:SerializedName("USD")
    val uSD: Double? = null,

    @field:SerializedName("CNY")
    val cNY: Double? = null,

    @field:SerializedName("NGN")
    val nGN: Double? = null
)