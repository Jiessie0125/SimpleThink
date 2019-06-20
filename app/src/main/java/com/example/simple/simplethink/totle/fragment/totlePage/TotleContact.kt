package com.example.simple.simplethink.totle.fragment.totlePage

import android.graphics.Bitmap
import com.example.simple.simplethink.model.Course
import com.example.simple.simplethink.model.TotleSortResponse
import okhttp3.ResponseBody

/**
 * Created by jiessie on 2019/6/5.
 */
interface TotleContact {

    interface  Presenter {
        fun getTotleSort()
        fun getItemImage(image: String,fileName : String)
        fun getCourse()
        fun getCourseImage(image: String,fileName : String)
    }

    interface View {
        fun getTotleSortIcon(list : List<TotleSortResponse>)
        fun getItemImage(imageName : String,image : Bitmap)
        fun setCourseAdapterView(list: List<Course>)
        fun getCourseImageView(imageName : String,image : Bitmap)
    }
}