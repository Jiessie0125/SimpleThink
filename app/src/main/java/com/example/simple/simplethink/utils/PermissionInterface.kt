package com.example.simple.simplethink.utils

/**
 * Created by Ashur on 2019/7/30.
 */
interface PermissionInterface {
    /**
     * 请求权限成功回调
     */
    fun requestPermissionsSuccess(callBackCode: Int);

    /**
     * 请求权限失败回调
     */
    fun requestPermissionsFail(callBackCode: Int);

}
