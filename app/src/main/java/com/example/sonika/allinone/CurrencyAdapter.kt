package com.example.sonika.allinone

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_currency_fragment.view.*

class CurrencyAdapter(val items: List<Currency>?) :
        RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items!![position])
    }


    override fun getItemCount(): Int = items!!.size

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val view = inflater.inflate(R.layout.adapter_currency_fragment, p0, false)
        return ViewHolder(view)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(currency: Currency) {
            itemView.textView_currency_name.text = currency.CurrencyName
            itemView.textView_currency_code.text = currency.CurrencyCode
            itemView.textView_rate.text = currency.ExchangeRate
            Log.e("url", Constants.BASE_URL + "/login_img/flags/" + currency.FlagCode)

            Picasso.get().load("https://demo.eremit.com.my/images/flags/" + currency.FlagCode + ".png").into(itemView.imageView_flag)

        }

    }
}