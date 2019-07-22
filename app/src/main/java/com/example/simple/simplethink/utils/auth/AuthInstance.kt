package com.example.simple.simplethink.utils.auth

class AuthInstance {
    var accessToken: String? = ""

    companion object {
        @Volatile
        private var instance: AuthInstance? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: AuthInstance().also { instance = it }
                }
    }


}