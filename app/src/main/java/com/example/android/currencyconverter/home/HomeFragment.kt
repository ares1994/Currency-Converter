package com.example.android.currencyconverter.home


import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.currencyconverter.*
import com.example.android.currencyconverter.util.Position
import com.example.android.currencyconverter.databinding.FragmentConverterBinding
import com.example.android.currencyconverter.util.Util
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus

const val NO_RATES = -1.00

class HomeFragment : Fragment() {


    private lateinit var binding: FragmentConverterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_converter, container, false
            )

        val application = requireNotNull(this.activity).application

        val viewModelFactory = HomeViewModelFactory(application)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)


        binding.apply {
            this.viewModel = viewModel

            //Automatically handles timeStamp setting even in cases where data has changed
            viewModel.currentRates.observe(this@HomeFragment, Observer {
                timeStampTextView.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                timeStampTextView.text = viewModel.setTimeStamp()
            })
            lifecycleOwner = this@HomeFragment

            //Hook spinners up to respective data lists
            baseCurrencySpinner.adapter = SpinnerAdapter(
                context!!,
                Util.baseSpinnersList
            )
            convertedCurrencySpinner.adapter =
                SpinnerAdapter(context!!, Util.conversionSpinnersList)

            //Handle conversion currency spinner actions
            convertedCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    secondCurrencyEditText.setText("")
//                    viewModel.setInfoForSpinnerItemSelected(position)
                    EventBus.getDefault().post(Position(position))
                    convertButton.performClick()
                }
            }
            convertButton.setOnClickListener {
                convertFromFirstEditText(binding)
            }


            //Configure Editexts for two-way conversion
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

    //Handles conversion from firstCurrencyEditText
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

    //Handles conversion from secondCurrencyEditText
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
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sign_up ->{
                openWebPage("https://dashboard.cowrywise.com/accounts/register/")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    //Opens webpage when sign-up is clicked
    private fun openWebPage(url: String) {
        val webPage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        startActivity(intent)
    }
}
