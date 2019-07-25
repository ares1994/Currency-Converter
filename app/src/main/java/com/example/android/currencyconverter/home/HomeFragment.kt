package com.example.android.currencyconverter.home


import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.currencyconverter.SpinnerAdapter
import com.example.android.currencyconverter.Util
import com.example.android.currencyconverter.databinding.FragmentConverterBinding

import com.example.android.currencyconverter.R


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


        val viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)


        binding.apply {
            this.viewModel = viewModel


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
                val input = firstCurrencyEditText.text.toString()
                if (input.isBlank()){
                    return@setOnClickListener
                }
                secondCurrencyEditText.setText("${viewModel.calculateValue(input)}")
            }
            firstCurrencyEditText.setOnEditorActionListener { textView, id, keyEvent ->
                if ((keyEvent != null && (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) || (id == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    convertButton.performClick()
                }

                return@setOnEditorActionListener false
            }
        }


        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }




}
