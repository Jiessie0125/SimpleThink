package com.example.simple.simplethink.login

import cn.sharesdk.framework.PlatformDb

/**
 * Created by Ashur on 2019/8/2.
 */

interface LoginContract {
    interface View {
        fun onLoginSuccess()
        fun onLoadUserInfoSuccess()
        fun onFailure(e: Throwable)
        fun loading()
        fun dismiss()

    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun login(platformDb: PlatformDb)
        fun loadUserInfo()
    }
}