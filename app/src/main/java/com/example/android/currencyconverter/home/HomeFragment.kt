package com.example.android.currencyconverter.home


import android.graphics.Paint
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.currencyconverter.SpinnerAdapter
import com.example.android.currencyconverter.Util
import com.example.android.currencyconverter.databinding.FragmentConverterBinding

import com.example.android.currencyconverter.R
import com.google.android.material.snackbar.Snackbar
const val NO_RATES = -1.00

class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)
        val binding: FragmentConverterBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_converter, container, false
            )
        val application = requireNotNull(this.activity).application

        val viewModelFactory = HomeViewModelFactory(application)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)


        binding.apply {
            this.viewModel = viewModel
            viewModel.currentRates.observe(this@HomeFragment, Observer {
                timeStampTextView.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                timeStampTextView.text = viewModel.setTimeStamp()
            })
            lifecycleOwner = this@HomeFragment
            baseCurrencySpinner.adapter = SpinnerAdapter(
                context!!,
                Util.baseSpinnersList
            )
            convertedCurrencySpinner.adapter = SpinnerAdapter(context!!, Util.conversionSpinnersList)
            convertedCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    secondCurrencyEditText.setText("")
                    viewModel.setInfoForSpinnerItemSelected(position)
                    convertButton.performClick()
                }
            }
            convertButton.setOnClickListener {
                convertFromFirstEditText(binding)
            }

            firstCurrencyEditText.setOnEditorActionListener { textView, id, keyEvent ->
                if ((keyEvent != null && (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) || (id == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    convertFromFirstEditText(binding)
                }
                return@setOnEditorActionListener false
            }

            secondCurrencyEditText.setOnEditorActionListener { textView, id, keyEvent ->
                if ((keyEvent != null && (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) || (id == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    convertFromSecondEditText(binding)
                }
                return@setOnEditorActionListener false
            }

        }


        return binding.root
    }

    private fun convertFromFirstEditText(binding: FragmentConverterBinding) {
        binding.apply {
            val input = firstCurrencyEditText.text.toString()
            if (input.isBlank()) {
                return
            }
            if (viewModel!!.calculateFirstEditValue(input) == NO_RATES) {
                Snackbar.make(binding.root, "Rates have not been retrieved yet", Snackbar.LENGTH_LONG).show()
                return
            }
            secondCurrencyEditText.setText("${viewModel!!.calculateFirstEditValue(input)}")
        }
    }

    private fun convertFromSecondEditText(binding: FragmentConverterBinding) {
        binding.apply {
            val input = secondCurrencyEditText.text.toString()
            if (input.isBlank()) {
                return
            }
            if (viewModel!!.calculateFirstEditValue(input) == NO_RATES) {
                Snackbar.make(binding.root, "Rates have not been retrieved yet", Snackbar.LENGTH_LONG).show()
                return
            }
            firstCurrencyEditText.setText("${viewModel!!.calculateSecondEditValue(input)}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }


}
