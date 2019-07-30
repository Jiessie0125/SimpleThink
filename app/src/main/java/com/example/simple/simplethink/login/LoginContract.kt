package com.example.simple.simplethink.login

/**
 * Created by 111 on 2019/7/18.
 */
interface LoginContract {

    interface View {
        fun onSuccess()
        fun onFailure(e: Throwable)

    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun login(userName: String, password: String)
    }
}