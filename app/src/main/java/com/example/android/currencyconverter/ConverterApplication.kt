package com.example.android.currencyconverter

import android.app.Application
import io.realm.Realm


class ConverterApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)


    }
}