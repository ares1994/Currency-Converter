package com.example.android.currencyconverter.home


import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.currencyconverter.*
import org.jetbrains.anko.doAsync

class HomeViewModel(application: ConverterApplication) : AndroidViewModel(application) {
    private val _currentRates = MutableLiveData<DatabaseRates>()
    val currentRates: LiveData<DatabaseRates> get() = _currentRates
    private var position: Int = 0
    private val _currentSpinnerSelected = MutableLiveData<String>()
    val currentSpinnerSelected: LiveData<String> get() = _currentSpinnerSelected


    init {
        ConverterRepo().refreshCurrencyRates()

        _currentRates.value = ConverterRepo().realm.find()

        ConverterRepo().realm.addChangeListener {
            _currentRates.value = it.find()
        }
        _currentSpinnerSelected.value = "USD"
    }


    fun setInfoForSpinnerItemSelected(position: Int) {
        _currentSpinnerSelected.value = Util.conversionSpinnersList[position].name
        this.position = position
    }


    fun calculateFirstEditValue(value: String): Double {
        if (currentRates.value == null) {
            return -1.00
        }
        return value.toDouble() * Util.appropriateRate(currentRates.value!!, position)
    }

    fun calculateSecondEditValue(value: String): Double {
        if (currentRates.value == null) {
            return -1.00
        }
        return value.toDouble() / Util.appropriateRate(currentRates.value!!, position)
    }


    fun setTimeStamp(): String {
        if (currentRates.value==null){
            return ""
        }
        return getApplication<ConverterApplication>().getString(
            R.string.timeStamp,
            Util.getStringTimeStampWithDate(_currentRates.value?.timeEntered!!)
        )
    }


    override fun onCleared() {
        super.onCleared()
        ConverterRepo().realm.removeAllChangeListeners()
    }
}