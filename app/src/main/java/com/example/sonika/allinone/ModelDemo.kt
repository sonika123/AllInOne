package com.example.sonika.allinone

// a model for objects of API

data class User(

        var Id:String,
        var Text : String
)

data class Currency(

        var ExchangeRateId : Int,
        var CountryId: Int,
        var ExchangeRate : String,
        var CountryCode3 : String,
        var CountryName : String,
        var CurrencyCode : String,
        var CurrencyName : String,
        var FlagCode : String,
        var LastWeekRates : List<LastWeekRate>
)

data class LastWeekRate(
        var Date : String,
        var Rate : String
)
