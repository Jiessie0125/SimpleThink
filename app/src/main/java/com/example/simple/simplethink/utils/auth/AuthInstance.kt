package com.example.simple.simplethink.utils.auth

import com.example.simple.simplethink.model.UserInfoResponse

class AuthInstance {
    var accessToken: String? = null
    var userInfo: UserInfoResponse? = null

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
    }

}