package com.example.sonika.allinone

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.sonika.allinone.ApiInterface.Companion.retrofit
import kotlinx.android.synthetic.main.fragment_currency.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyFragment : BaseFragment() {


    override var layoutInt: Int
        get() = R.layout.fragment_currency
        set(value) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.setTitle("Countries and Currencies")
        currencyOperation()

    }

    private fun currencyOperation() {
        if (activity!!.isOnline()) {
            var client = retrofit.create(ApiInterface::class.java)
            val callExchangeRate = client.getexchangerate()


//          val flagImage =   client.getFlagImage()
            callExchangeRate.enqueue(object : Callback<List<Currency>> {
                override fun onFailure(call: Call<List<Currency>>?, t: Throwable?) {

                    activity!!.toastmessage("failed")
                }

                override fun onResponse(call: Call<List<Currency>>?, response: Response<List<Currency>>?) {
                    val responseCurrency = response?.body()
                    recyclerview_list.layoutManager = LinearLayoutManager(context)
                    recyclerview_list.adapter = CurrencyAdapter(responseCurrency)
                }

            })
        }


    }
}