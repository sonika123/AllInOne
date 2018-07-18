package com.example.sonika.allinone


import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator

open class BaseFragment() : Fragment(){


    open fun openFragment(fragment: Fragment) {

        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}