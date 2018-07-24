package com.example.sonika.allinone

import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface
{

    @GET("list/occupation")



fun getOccupation() :
            Call<List<User>>

    @GET("list/exchangerate")
    fun getexchangerate() :
            Call<List<Currency>>

//    @GET("/login_img/flags/{imageName}")
//    fun getFlagImage(@Path("imageName") imageName : String)

    companion object {

        var httpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))


        var builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
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
