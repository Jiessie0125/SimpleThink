package com.example.simple.simplethink.login

/**
 * Created by Ashur on 2019/8/2.
 */

interface LoginContract {
    interface View {
        fun onSuccess()
        fun onFailure(e: Throwable)
        fun loading()
        fun dismiss()

    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun login(userName: String)
    }
}