package com.example.android.currencyconverter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.android.currencyconverter.databinding.ItemSpinnerBinding
import kotlinx.android.synthetic.main.item_spinner.view.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SpinnerAdapter(context: Context, list: List<CountryItem> ) : ArrayAdapter<CountryItem>(context, 0, list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }


    private fun initView(position: Int,convertView: View?,parent: ViewGroup): View{
       val itemSpinnerBinding : ItemSpinnerBinding =
               DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.item_spinner,parent,false)
        itemSpinnerBinding.countryLabelTextView.text = getItem(position).name
        itemSpinnerBinding.circleImageView.setImageResource(getItem(position).flagResource)
        return itemSpinnerBinding.root
    }
}
