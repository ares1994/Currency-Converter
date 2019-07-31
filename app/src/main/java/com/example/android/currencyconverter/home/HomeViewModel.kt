package com.example.android.currencyconverter.home


import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.currencyconverter.*
import com.example.android.currencyconverter.repository.ConverterRepo
import com.example.android.currencyconverter.repository.find
import com.example.android.currencyconverter.util.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

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

        EventBus.getDefault().register(this)
    }

    //Handles currency calculation using input from FirstCurrencyEditText
    fun calculateFirstEditValue(value: String): Double {
        if (currentRates.value == null) {
            return -1.00
        }
        return value.toDouble() * Util.appropriateRate(currentRates.value!!, position)
    }

    //Handles reverse currency calculation using input from SecondCurrencyEditText
    fun calculateSecondEditValue(value: String): Double {
        if (currentRates.value == null) {
            return -1.00
        }
        return value.toDouble() / Util.appropriateRate(currentRates.value!!, position)
    }

    //Handles timeStamp setting
    fun setTimeStamp(): String {
        if (currentRates.value == null) {
            return ""
        }
        return getApplication<ConverterApplication>().getString(
            R.string.timeStamp,
            Util.getStringTimeStampWithDate(_currentRates.value?.timeEntered!!)
        )
    }

    //Removes all listeners
    override fun onCleared() {
        super.onCleared()
        ConverterRepo().realm.removeAllChangeListeners()
        EventBus.getDefault().unregister(this)
    }

//Handles the setting of appropriate rates when spinner is changed
    @Subscribe
    fun positionEvent(position: Position) {
        _currentSpinnerSelected.value = Util.conversionSpinnersList[position.value].name
        this.position = position.value
    }


}