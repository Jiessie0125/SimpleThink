package com.example.simple.simplethink.main.UserInfoActivity

import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.model.BottomActivityResponse
import com.example.simple.simplethink.model.SuggestedCourse
import com.example.simple.simplethink.model.UploadFileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File

/**
 * Created by mobileteam on 2019/8/13.
 */
interface UserInfoContract {
    interface View {
        fun onUpdateUserInfoSuccess()
        fun onUploadFileSuccess(message: UploadFileResponse)
        fun onRefreshUerInfoSuccess()
        fun onFailure(e: Throwable)
    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun uploadFile(avata: MultipartBody.Part)
        fun updateUserInfo(avatar:String?, nickname: String?)
        fun loadUserInfo()
    }
}