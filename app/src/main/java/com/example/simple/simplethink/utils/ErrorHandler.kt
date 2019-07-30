package com.example.simple.simplethink.utils

import android.content.Context
import android.widget.Toast
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.ErrorResponse
import com.google.gson.GsonBuilder
import retrofit2.HttpException

/**
 * Created by jiessie on 2019/7/30.
 */
class ErrorHandler {

    companion object {
        private val ERROR_MAP = HashMap<Int, Int>()

        fun showErrorWithToast(context: Context, e: Throwable) {
            val msg = getErrorMsg(context, e)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        fun showErrorWithToast(context: Context, msgId: Int) {
            if (msgId <= 0) {
                return
            }
            Toast.makeText(context, context.getText(msgId), Toast.LENGTH_LONG).show()
        }

        private fun getErrorMsg(context: Context, e: Throwable): String {
            var errorMsg = R.string.unknow_error
            if (e is HttpException) {
                try {
                    val errorContent = e.response().errorBody()?.string()
                    val errorResponse = GsonBuilder().create().fromJson<ErrorResponse>(errorContent, ErrorResponse::class.java)
                    errorMsg = ERROR_MAP.get(errorResponse.errorCode) ?: R.string.unknow_error
                } catch (e: Exception) {
                    errorMsg = R.string.unknow_error
                }

            }
            return context.getString(errorMsg)
        }

        init {
            ERROR_MAP.put(1117, R.string.within_the_discount_period)
            ERROR_MAP.put(1116, R.string.used_code)
            ERROR_MAP.put(1115, R.string.invalid_code)
            ERROR_MAP.put(1114, R.string.invalid_request_oauth)
            ERROR_MAP.put(1113, R.string.refresh_token_invalid)
            ERROR_MAP.put(1112, R.string.unsupported_grant_type)
            ERROR_MAP.put(1111, R.string.invalid_client)
            ERROR_MAP.put(1110, R.string.invalid_password_oauth)
            ERROR_MAP.put(1025, R.string.old_password_wrong2)
            ERROR_MAP.put(1024, R.string.update_password_fail2)
            ERROR_MAP.put(1023, R.string.invalid_username_parameter2)
            ERROR_MAP.put(1022, R.string.invalid_subscription_id_parameter)
            ERROR_MAP.put(1021, R.string.invalid_sign_parameter2)
            ERROR_MAP.put(1020, R.string.username_not_existed)
            ERROR_MAP.put(1019, R.string.invalid_file_type)
            ERROR_MAP.put(1018, R.string.invalid_course_id_parameter)
            ERROR_MAP.put(1017, R.string.invalid_gender_parameter)
            ERROR_MAP.put(1016, R.string.invalid_range_location_parameter)
            ERROR_MAP.put(1015, R.string.invalid_range_age_parameter)
            ERROR_MAP.put(1014, R.string.update_password_fail)
            ERROR_MAP.put(1013, R.string.old_password_wrong)
            ERROR_MAP.put(1012, R.string.update_user_info_fail)
            ERROR_MAP.put(1011, R.string.invalid_nickname_parameter)
            ERROR_MAP.put(1010, R.string.user_not_exist)
            ERROR_MAP.put(1009, R.string.signup_fail)
            ERROR_MAP.put(1008, R.string.invalid_sign_parameter)
            ERROR_MAP.put(1007, R.string.username_existed)
            ERROR_MAP.put(1006, R.string.invalid_app_version_parameter)
            ERROR_MAP.put(1005, R.string.invalid_system_version_parameter)
            ERROR_MAP.put(1004, R.string.invalid_device_name_parameter)
            ERROR_MAP.put(1003, R.string.invalid_device_id_parameter)
            ERROR_MAP.put(1002, R.string.invalid_platform_parameter)
            ERROR_MAP.put(1001, R.string.invalid_password_parameter)
            ERROR_MAP.put(1000, R.string.invalid_username_parameter)
            ERROR_MAP.put(999, R.string.unknow_error)
        }
    }
}