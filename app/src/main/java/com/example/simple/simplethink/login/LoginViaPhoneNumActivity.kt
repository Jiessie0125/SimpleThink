package com.example.simple.simplethink.login

import android.app.Activity
import android.os.Bundle
import com.example.simple.simplethink.R
import kotlinx.android.synthetic.main.activity_login_phone_number.*

/**
 * Created by Ashur on 2019/7/18.
 */
class LoginViaPhoneNumActivity : Activity(), LoginContract.View {
    private var presenter: LoginContract.Presenter = LoginPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_phone_number)
        presenter.bind(this)
        login_phone_btn.setOnClickListener{
            val phone = login_phone_number.text.toString()
            val psd = login_phone_number_pwd.text.toString()
            presenter.login(phone,psd)
        }
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    override fun onSuccess() {

    }

    override fun onFailure() {

    }
}