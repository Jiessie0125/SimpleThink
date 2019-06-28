package com.example.simple.simplethink.totle.fragment.scenePage

import android.graphics.Bitmap
import com.example.simple.simplethink.model.Course
import com.example.simple.simplethink.model.SceneItem
import com.example.simple.simplethink.model.ScenesResponse
import com.example.simple.simplethink.model.TotleSortResponse

/**
 * Created by jiessie on 2019/6/5.
 */
interface SceneContact {

    interface  Presenter {
        fun getAllScene()
    }

    interface View {
        fun setSenceAdapter(scenesResponse : ArrayList<SceneItem>)
    }
}