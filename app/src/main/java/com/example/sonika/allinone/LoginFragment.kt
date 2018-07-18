package com.example.sonika.allinone

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.sonika.allinone.R.id.text_input_email
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import kotlinx.android.synthetic.main.fragment_login.*
import com.mobsandgeeks.saripaar.Validator
import kotlinx.android.synthetic.*

class LoginFragment : BaseFragment(), Validator.ValidationListener {

    @NotEmpty
    @Email
    @BindView(R.id.text_input_email)
    lateinit var email: TextInputEditText

    @Password
    @BindView(R.id.text_input_password)
    lateinit var password: TextInputEditText
    private var validator: Validator? = null

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {

          val error = errors!!.get(0)
          val message = error.getCollatedErrorMessage(activity)
          val errorText = error.getView() as TextInputEditText
          errorText.setError(message)
          errorText.requestFocus()
    }

    override fun onValidationSucceeded() {
        activity!!.toastmessage("Validated!")
        openFragment(FormFragment())
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValidate()


        val sharedPreferences = (activity as AppCompatActivity).getSharedPreferences("USER_LOGIN", 0)

        if (sharedPreferences.getString("loggedIn", "").toString().equals("loggedIn")) {
            openFragment(FormFragment())
        }

        btn_login.setOnClickListener(View.OnClickListener {
            initValidate()
            validator?.validate()
           /* if (validator == null) {

            } else
               */

        })
    }

    fun initValidate() {
        validator = Validator(this)
        validator?.setValidationListener(this)
    }


    //            if(text_input_email.text!!.length >  2)
////                if (text_input_email.text!!.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")))
//                    if (text_input_email.text!!.matches(Regex("demo@demo.com")))
//                    {
//                        if (text_input_password.text!!.matches(Regex("demo"))) {
//                            val editor = sharedPreferences.edit()
//                            editor.putString("loggedIn", "loggedIn")
//                            editor.apply()
//                            editor.commit()
//            openFragment(FormFragment())
//                        }
//                        else
//                            text_input_password.error = "invalid password"
//
//                    }
//                    else
//                        text_input_email.error = "Invalid Email or Password"
//        })

//            if(text_input_email.text!!.length >  2)
//            if (text_input_email.text!!.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")))
//           if (text_input_email.text!!.matches(Regex("demo@demo.com")))
//            {
//                if (text_input_password.text!!.matches(Regex("demo"))) {
//                    val editor = sharedPreferences.edit()
//                    editor.putString("loggedIn", "loggedIn")
//                    editor.apply()
//                    editor.commit()
//                    openFragment(FormFragment())
//                }
//                else
//                    text_input_password.error = "invalid password"
//
//            }
//                else
//                text_input_email.error = "Invalid Email or Password"


}

//
//    private fun openFragment() {
//
//        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.container, FormFragment())
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//    }



