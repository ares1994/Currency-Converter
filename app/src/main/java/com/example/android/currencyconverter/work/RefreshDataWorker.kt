package com.example.android.currencyconverter.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.android.currencyconverter.repository.ConverterRepo

import retrofit2.HttpException

class RefreshDataWorker(appContext: Context,params: WorkerParameters) : Worker(appContext,params) {

    companion object{
        const val WORK_NAME = "refresh_rates"
    }

    //Defines worker
    override fun doWork(): Result {

        return try {
             ConverterRepo().refreshCurrencyRates()
             Result.success()
        } catch (e: HttpException){
             Result.retry()
        }


    }

}