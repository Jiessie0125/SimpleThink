package com.example.simple.simplethink.netapi.auth

import com.example.simple.simplethink.model.AuthResponse
import com.example.simple.simplethink.network.RetrofitServiceManager
import io.reactivex.Observable

/**
 * Created by Ashur on 2019/7/18.
 */
class AuthRepositoryImp : AuthRepository {

    override fun auth(userName: String, password: String): Observable<AuthResponse> {
        val service = RetrofitServiceManager.instance.create(AuthApiService::class.java)
        val params = HashMap<String, String>()
        params.put("username", userName)
        params.put("password", password)
        return service.auth(params)
    }

}