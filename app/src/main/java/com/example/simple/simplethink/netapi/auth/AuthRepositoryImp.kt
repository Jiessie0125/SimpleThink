package com.example.simple.simplethink.netapi.auth

import com.example.simple.simplethink.model.AuthResponse
import com.example.simple.simplethink.model.CheckIsUserExistResponse
import com.example.simple.simplethink.network.RetrofitServiceManager
import com.example.simple.simplethink.utils.DeviceUtils
import io.reactivex.Observable

/**
 * Created by Ashur on 2019/7/18.
 */
class AuthRepositoryImp : AuthRepository {

    override fun checkIsUserExist(userName: String): Observable<CheckIsUserExistResponse> {
        val service = RetrofitServiceManager.instance.create(AuthApiService::class.java)
        val params = HashMap<String, String>().apply {
            put("username", userName)
        }
        return service.checkUserIsExist(params)
    }

    override fun auth(userName: String, password: String): Observable<AuthResponse> {
        val service = RetrofitServiceManager.instance.create(AuthApiService::class.java)
        val params = HashMap<String, String>().apply {
            put("username", userName)
            put("password", password)
            put("client_secret", "3f54abb72023eeb7f32370242ffce0926b73aa24")
            put("client_id", "android")
            put("grant_type", "password")
            put("platform", "android")
            put("device_name", android.os.Build.MODEL)
            put("device_id", DeviceUtils.getDeviceId())
            put("system_version", android.os.Build.VERSION.SDK_INT.toString())
            put("app_version", DeviceUtils.getAppVersionName())
        }
        return service.auth(params)
    }

}