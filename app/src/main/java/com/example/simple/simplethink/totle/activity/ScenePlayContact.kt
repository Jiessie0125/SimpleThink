package com.example.simple.simplethink.totle.activity

import com.example.simple.simplethink.main.MainContract
import com.example.simple.simplethink.model.*

/**
 * Created by jiessie on 2019/6/5.
 */
interface ScenePlayContact {

    interface View {
        fun onuploadPracticeSuccess()
        fun onLoadUserInfoSuccess()
        fun onFailure(e: Throwable)
    }

    interface  Presenter {
        fun bind(view: ScenePlayContact.View)
        fun unbind()
        fun uploadPractice(params: String)
        fun loadUserInfo()
    }

}