package com.example.simple.simplethink.utils.auth

import com.example.simple.simplethink.MyApp
import com.example.simple.simplethink.model.UserInfoResponse
import com.example.simple.simplethink.utils.SharedPreferencesUtil

class AuthInstance {
    var accessToken: String? = null
    var userInfo: UserInfoResponse? = null
    var isVip: Boolean= false
    var orderId : String ?= null

    companion object {
        const val AUTH = "AUTH"
        const val REFRESHTOKEN = "REFRESHTOKEN"
        @Volatile
        private var singleton: AuthInstance? = null

        fun getInstance() =
                singleton ?: synchronized(this) {
                    singleton ?: AuthInstance().also { singleton = it }
                }
    }

    fun clear() {
        accessToken = null
        userInfo = null
        singleton = null
        isVip = false
        orderId = null
        SharedPreferencesUtil.removeString(MyApp.context!!,AuthInstance.AUTH)
        SharedPreferencesUtil.removeString(MyApp.context!!,AuthInstance.REFRESHTOKEN)
    }

}