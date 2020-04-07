package com.example.simple.simplethink.main.updatepassword

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.utils.auth.AuthInstance
import kotlinx.android.synthetic.main.activity_update_password.*

/**
 * Created by mobileteam on 2019/8/13.
 */
class UpdatePasswordActivity : BaseActivity(), UpdatePasswordContract.View {

    private var presenter: UpdatePasswordContract.Presenter = UpdatePasswordPresenter()

    override fun onUpdatePasswordActivitySuccess() {
        Toast.makeText(this, R.string.update_password_success, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onFailure(e: Throwable) {
        ErrorHandler.showErrorWithToast(this, e)
    }

    companion object {
        fun newIntent (context: Context?) : Intent {
            var intent = Intent(context, UpdatePasswordActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)
        presenter.bind(this)
        registerTitle.setOnClickListener {
            finish()
        }
        submit_btn.setOnClickListener {
            if (validation()){
                return@setOnClickListener
            }

            val password_old = old_passwrd.getText().toString()
            val password_new = confirm_new_password.getText().toString()
            val userName = AuthInstance.getInstance().userInfo?.userName
            presenter.updatePassword(password_old, password_new, userName!!, "")

        }
    }

    private fun validation(): Boolean {
        val oldpwd = old_passwrd.getText().toString()
        val pwdText1 = new_password.getText().toString()
        val pwdText2 = confirm_new_password.getText().toString()


        if (oldpwd.length <6 || pwdText1.length < 6 || pwdText2.length < 6) {
            Toast.makeText(this, R.string.password_is_lower_minimum, Toast.LENGTH_LONG).show()
            return true
        }

        if (oldpwd.length >18 || pwdText1.length > 18 || pwdText2.length > 18) {
            Toast.makeText(this, R.string.password_is_exceed_maximum, Toast.LENGTH_LONG).show()
            return true
        }

        if (pwdText1 != pwdText2) {
            Toast.makeText(this, R.string.password_is_not_align, Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }
}