package com.example.android.currencyconverter

import android.app.Application
import androidx.work.*
import com.example.android.currencyconverter.work.RefreshDataWorker
import io.realm.Realm
import org.jetbrains.anko.doAsync
import java.util.concurrent.TimeUnit


class ConverterApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        delayedInit()

    }

    private fun delayedInit() {
        doAsync {
            setupRecurringWork()
        }
    }

    //Method for invoking Worker that refreshes rates everyday in the background under constraints defined below
    private fun setupRecurringWork() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .setRequiresCharging(true)
            .build()

        val request = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            1,TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request

        )
    }
}