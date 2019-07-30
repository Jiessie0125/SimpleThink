package com.example.simple.simplethink.utils

import android.content.pm.PackageManager
import android.provider.Settings
import com.example.simple.simplethink.MyApp
import java.util.*


class DeviceUtils {
    companion object {
        fun getAppVersionCode(): Int {
            val pm = MyApp.context?.packageManager
            try {
                val packageInfo = pm?.getPackageInfo(MyApp.context?.packageName, 0)
                return packageInfo?.versionCode ?: 0
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return 0
        }

        fun getAppVersionName(): String {
            val pm = MyApp.context?.packageManager
            try {
                val packageInfo = pm?.getPackageInfo(MyApp.context?.packageName, 0)
                return packageInfo?.versionName ?: ""
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return ""
        }

        fun getDeviceId(): String {
            val androidId = Settings.Secure.getString(
                    MyApp.context?.contentResolver,
                    Settings.Secure.ANDROID_ID

            )
            return UUID(androidId.hashCode() * 1L, androidId.hashCode() * 1L shl 32).toString()

        }
    }
}