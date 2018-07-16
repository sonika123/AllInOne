package com.example.sonika.allinone

import android.content.Context
import android.widget.Toast
import java.util.*


fun Context.toastmessage(message : String, duration : Int = Toast.LENGTH_LONG)
{
    Toast.makeText(this, message, duration).show()
}