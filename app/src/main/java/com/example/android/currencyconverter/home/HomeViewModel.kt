package com.example.android.currencyconverter.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.currencyconverter.ConverterRepo
import com.example.android.currencyconverter.Util

class HomeViewModel : ViewModel() {
    private val _currentRates = MutableLiveData<DatabaseRates>()
    val currentRates: LiveData<DatabaseRates> get() = _currentRates
    private var position: Int = 0
    private val _currentSpinnerSelected = MutableLiveData<String>()
    val currentSpinnerSelected: LiveData<String> get() = _currentSpinnerSelected


    init {
        ConverterRepo().refreshCurrencyRates()

        ConverterRepo().realm.addChangeListener {
            val id = 1
            _currentRates.value = it.where(DatabaseRates::class.java).equalTo("id", id).findFirst()
        }
        _currentSpinnerSelected.value = "USD"
    }


    fun setInfoForSpinnerItemSelected(position: Int) {
        _currentSpinnerSelected.value = Util.conversionSpinnersList[position].name
        this.position = position
    }


    fun calculateValue(value: String): Double {
        return value.toDouble() * Util.appropriateRate(currentRates.value!!, position)


    }


}