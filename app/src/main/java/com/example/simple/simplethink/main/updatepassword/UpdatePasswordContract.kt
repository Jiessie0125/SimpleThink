package com.example.simple.simplethink.main.updatepassword

/**
 * Created by mobileteam on 2019/8/13.
 */
interface UpdatePasswordContract {

    interface View {
        fun onUpdatePasswordActivitySuccess()
        fun onFailure(e: Throwable)
    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun updatePassword(password_old: String, password_new: String, username: String, code: String)
    }
}