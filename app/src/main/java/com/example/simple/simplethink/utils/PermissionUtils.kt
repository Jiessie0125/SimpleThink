package com.example.simple.simplethink.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


/**
 * Created by Vincent on 2019/7/30.
 */

class PermissionUtils{
    companion object {
        lateinit var mActivity: Activity
        lateinit var mPermissionInterface: PermissionInterface
        var callBackCode: Int =0

        /**
         * 判断是否有某个权限
         *
         * @param context
         * @param permission
         * @return
         */
        fun hasPermission( context:Context,  permission:Array<String>): Boolean{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Android 6.0判断，6.0以下跳过。在清单文件注册即可，不用动态请求，这里直接视为有权限
                for (value in permission){
                    if (context.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }

            }
            return true;
        }

        /**
         *  请求权限
         * @param permission 权限名字
         * @param callBackCode 回调code
         */
        fun requestPermissions(permission:Array<String>, callBackCode:Int,activity: Activity, permissionInterface: PermissionInterface) {
            //this.permission = permission
            this.callBackCode = callBackCode
            this.mActivity = activity
            this.mPermissionInterface = permissionInterface
            if (hasPermission(mActivity, permission)) {
                mPermissionInterface.requestPermissionsSuccess(callBackCode);
            } else {
                mActivity.requestPermissions(permission, callBackCode);
            }

        }

        /**
         *  在Activity中的onRequestPermissionsResult中调用,用来接收结果判断
         * @param requestCode
         * @param permissions
         * @param grantResults
         */
        public fun requestPermissionsResult( requestCode:Int, permissions:Array<String>, grantResults:IntArray) {
            if (requestCode == callBackCode) {
                for (value in grantResults){
                    if (value == PackageManager.PERMISSION_GRANTED) {
                        mPermissionInterface.requestPermissionsSuccess(callBackCode);
                    } else {
                        mPermissionInterface.requestPermissionsFail(callBackCode);
                    }
                }
            }
        }
    }


}