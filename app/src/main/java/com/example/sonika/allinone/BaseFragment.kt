package com.example.sonika.allinone


import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.ButterKnife
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator

abstract class BaseFragment() : Fragment(), Validator.ValidationListener{
abstract var layoutInt: Int
    open fun openFragment(fragment: Fragment) {

        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(layoutInt, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValidate()
    }


    var validator: Validator? = null



    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
//
        val error = errors!!.get(0)
        val message = error.getCollatedErrorMessage(activity)
        val errorText = error.getView() as TextInputEditText
        errorText.setError(message)
        errorText.requestFocus()

    }


    override fun onValidationSucceeded() {


    }

    fun initValidate() {
        validator = Validator(this)
        validator?.setValidationListener(this)
    }

    fun validate() : Boolean{
        validator?.validate()
        return true
    }
/*
    fun initializeValidation()
    {
        initValidate()
        validator?.validate()
    }*/
}