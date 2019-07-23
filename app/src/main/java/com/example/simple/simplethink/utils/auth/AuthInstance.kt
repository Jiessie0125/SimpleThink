package com.example.simple.simplethink.utils.auth

class AuthInstance {
    var accessToken: String? = ""

    companion object {
        @Volatile
        private var singleton: AuthInstance? = null

        fun getInstance() =
                singleton ?: synchronized(this) {
                    singleton ?: AuthInstance().also { singleton = it }
                }
    }


}