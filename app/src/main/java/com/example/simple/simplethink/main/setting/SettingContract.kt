package com.example.simple.simplethink.main

import android.app.Activity
import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.model.BottomActivityResponse
import com.example.simple.simplethink.model.SuggestedCourse
import okhttp3.ResponseBody

/**
 * Created by 111 on 2019/7/18.
 */
interface SettingContract {

    interface View {
        fun onLogoffSuccess(message: ResponseBody)
        fun onFailure(e: Throwable)

    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun appLogoff()
    }
}