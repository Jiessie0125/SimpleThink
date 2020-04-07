package com.example.simple.simplethink.login

/**
 * Created by 111 on 2019/7/18.
 */
interface ForgetPasswordContract {

    interface View {
        fun onSuccess()
        fun onRegisterSuccess()
        fun onFailure(e:Throwable)

    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun sendSMS(phoneNumber: Long)
        fun updateUserInfo(password_old: String, password_new: String, username: String, code: String)
        fun register(password_old: String, password_new: String, username: String, code: String)
    }
}