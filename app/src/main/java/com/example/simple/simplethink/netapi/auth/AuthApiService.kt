package com.example.simple.simplethink.netapi.auth

import com.example.simple.simplethink.model.AuthResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

/**
 * Created by Ashur on 2019/7/18.
 */
interface AuthApiService {

    @POST("oauth/access_token")
    fun auth(@Body params: Map<String, String>): @JvmSuppressWildcards Observable<AuthResponse>
}