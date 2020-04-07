package com.example.simple.simplethink.main.UserInfoActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.main.updatepassword.UpdatePasswordActivity
import com.example.simple.simplethink.model.UploadFileResponse
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.utils.auth.AuthInstance
import kotlinx.android.synthetic.main.activity_update_nick_name.*

/**
 * Created by mobileteam on 2019/8/13.
 */
class UpdateNickNameActivity: BaseActivity(), UserInfoContract.View{
    override fun onRefreshUerInfoSuccess() {
        Toast.makeText(this, R.string.update_user_info_success, Toast.LENGTH_LONG).show()
        finish()
    }

    companion object {
        fun newIntent (context: Context?) : Intent {
            var intent = Intent(context, UpdateNickNameActivity::class.java)
            return intent
        }
    }

    private var presenter: UserInfoContract.Presenter = UserInfoPresenter()

    override fun onUpdateUserInfoSuccess() {
        presenter.loadUserInfo()
    }

    override fun onUploadFileSuccess(message: UploadFileResponse) {
    }

    override fun onFailure(e: Throwable) {
        ErrorHandler.showErrorWithToast(this, e)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_nick_name)
        presenter.bind(this)
        registerTitle.setOnClickListener {
            finish()
        }
        val userInfo = AuthInstance.getInstance().userInfo
        new_nick_name.text = Editable.Factory.getInstance().newEditable(userInfo?.nickName)
        submit_btn.setOnClickListener {
            val nikeName = new_nick_name.text.toString()
            if(nikeName.length == 0){
                Toast.makeText(this, R.string.nike_name_cannot_null, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else{
                presenter.updateUserInfo(null,nikeName)
            }
        }

    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }
}