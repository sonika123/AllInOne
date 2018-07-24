package com.example.sonika.allinone

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password

class LoginFragment : BaseFragment() {
    @OnClick(R.id.btn_login)
    fun login() {
        validate()
    }

    override var layoutInt: Int
        get() = R.layout.fragment_login
        set(value) {}

    @NotEmpty
    @Email
    @BindView(R.id.text_input_email)
    lateinit var email: TextInputEditText

    @Password
    @BindView(R.id.text_input_password)
    lateinit var password: TextInputEditText


    override fun onValidationSucceeded() {
        activity!!.toastmessage("Validated!")
        openFragment(FormFragment())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = (activity as AppCompatActivity).getSharedPreferences("USER_LOGIN", 0)

        if (sharedPreferences.getString("loggedIn", "").toString().equals("loggedIn")) {
            openFragment(FormFragment())
        }


    }
}