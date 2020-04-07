package com.example.simple.simplethink.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.utils.ValidationUtils
import com.example.simple.simplethink.widget.WaitDialog
import kotlinx.android.synthetic.main.activity_login_phone_number.*

/**
 * Created by Ashur on 2019/7/18.
 */
class LoginViaPhoneNumActivity : BaseActivity(), LoginViaPhoneContract.View {

    companion object {
        open fun newIntent(context: Context): Intent {
            return Intent(context, LoginViaPhoneNumActivity::class.java)
        }
    }

    override fun onFailure(e: Throwable) {
        ErrorHandler.showErrorWithToast(this, e)
    }

    private var waitDialog: WaitDialog? = null
    private var presenter: LoginViaPhoneContract.Presenter = LoginViaPhonePresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_phone_number)
        waitDialog = WaitDialog(this)
        presenter.bind(this)
        login_phone_btn.setOnClickListener {
            if (!checkIsPhoneNumber()) {
                ErrorHandler.showErrorWithToast(this, R.string.invalid_phone_number_format)
                return@setOnClickListener
            }
            val phone = login_phone_number.text.toString()
            val psd = login_phone_number_pwd.text.toString()
            presenter.login(phone, psd)
        }
        forget_password_link.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            intent.putExtra("model", "forgetPassword")
            startActivity(intent)
        }
        login_phone_back_btn.setOnClickListener {
            finish();
        }

    }

    override fun showLoading() {
        waitDialog?.show()
    }

    override fun dismissLoading() {
        waitDialog?.dismiss()
    }

    private fun checkIsPhoneNumber(): Boolean {
        val phoneNumber = login_phone_number.text.toString()
        return ValidationUtils.isMobileNO(phoneNumber)
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    override fun onSuccess() {
        setResult(RESULT_OK)
        finish()
    }
}