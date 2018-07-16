package com.example.sonika.allinone

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface
{
//    var BASE_URL = "https://demo.eremit.com.my/api/"

    @GET("list/occupation")

fun getOccupation() :
            Call<List<User>>

    companion object {

        var httpClient = OkHttpClient.Builder()


        var builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl("https://demo.eremit.com.my/api/")
                .addConverterFactory(
                        GsonConverterFactory.create()
                )

        var retrofit = builder
                .client(
                        httpClient.build()
                )
                .build()

        var client = retrofit.create<ApiInterface>(ApiInterface::class.java)
    }
}
