package com.example.android.currencyconverter.network


import com.google.gson.annotations.SerializedName

//Kotlin object representation of JSON retrieved from Fixer.io
data class Response(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("rates")
	val rates: Rates? = null,

	@field:SerializedName("timestamp")
	val timestamp: Int? = null,

	@field:SerializedName("base")
	val base: String? = null
)