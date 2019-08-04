package com.example.simple.simplethink.netapi.auth

import com.example.simple.simplethink.model.AuthResponse
import com.example.simple.simplethink.model.CheckIsUserExistResponse
import io.reactivex.Observable

/**
 * Created by Ashur on 2019/7/18.
 */
interface AuthRepository {
    fun auth(userName: String, password: String): Observable<AuthResponse>

    fun checkIsUserExist(userName: String): Observable<CheckIsUserExistResponse>
}