package com.example.simple.simplethink.totle.activity

import android.graphics.Bitmap
import com.example.simple.simplethink.model.Course
import com.example.simple.simplethink.model.TotleSortResponse

/**
 * Created by jiessie on 2019/6/5.
 */
interface SceneDetailContact {

    interface  Presenter {
        fun getSceneItemMp3(url : String)
    }

    interface View {
       fun updateUi()
    }
}