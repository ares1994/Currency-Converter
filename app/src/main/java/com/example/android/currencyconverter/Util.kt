package com.example.android.currencyconverter

import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {

        val conversionSpinnersList = listOf(
            CountryItem("USD", R.drawable.united_states_of_america_640),
            CountryItem("NGN", R.drawable.nigeria_640),
            CountryItem("AED", R.drawable.united_arab_emirates_640),
            CountryItem("GBP", R.drawable.united_kingdom_640),
            CountryItem("JPY", R.drawable.japan_640),
            CountryItem("CNY", R.drawable.china_640)
        )

        val baseSpinnersList = listOf(CountryItem("EU", R.drawable.european_union_640))

        fun appropriateRate(databaseRates: DatabaseRates, position: Int): Double{
            var rate: Double = 0.00
            when(position){
                0-> rate = databaseRates.uSD!!
                1-> rate = databaseRates.nGN!!
                2-> rate = databaseRates.aED!!
                3-> rate = databaseRates.gBP!!
                4-> rate = databaseRates.jPY!!
                5-> rate = databaseRates.cNY!!
            }
            return rate
        }


        fun getStringTimeStampWithDate(date: Long): String {
            val dateFormat = SimpleDateFormat("HH:mm z",
                Locale.getDefault())
            dateFormat.timeZone = TimeZone.getDefault()
            return dateFormat.format(date).toString()
        }


    }
}