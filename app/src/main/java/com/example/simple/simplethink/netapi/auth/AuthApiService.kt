package com.example.simple.simplethink.netapi.auth

import com.example.simple.simplethink.model.AuthResponse
import com.example.simple.simplethink.model.UserInfoResponse
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*

/**
 * Created by Ashur on 2019/7/18.
 */
interface AuthApiService {

    @FormUrlEncoded
    @POST("/oauth/access_token")
    fun auth(@FieldMap params: Map<String, String>): Observable<AuthResponse>

    @GET("/user")
    fun getUserInfo(): Observable<UserInfoResponse>
}