package com.example.simple.simplethink.totle.fragment.whiteNoisePage

import android.graphics.Bitmap
import com.example.simple.simplethink.model.Course
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.model.WhiteNoiseItemResponse

/**
 * Created by jiessie on 2019/6/5.
 */
interface WhiteNoiseContact {

    interface  Presenter {
        fun getWhiteNoise()
    }

    interface View {
        fun updateView(list : List<WhiteNoiseItemResponse>)

    }
}