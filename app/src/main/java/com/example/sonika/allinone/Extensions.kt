package com.example.sonika.allinone

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import java.util.*


fun Context.toastmessage(message : String, duration : Int = Toast.LENGTH_LONG)
{
    Toast.makeText(this, message, duration).show()
}

fun Context.isOnline() : Boolean
{
    val cm = applicationContext!!.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
    return isConnected
}