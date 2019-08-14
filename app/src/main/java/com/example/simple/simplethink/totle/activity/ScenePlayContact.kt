package com.example.simple.simplethink.totle.activity

import com.example.simple.simplethink.model.CourseLogs

/**
 * Created by jiessie on 2019/6/5.
 */
interface ScenePlayContact {

    interface  Presenter {
        fun uploadPractice(params:  ArrayList<CourseLogs>)
    }

}