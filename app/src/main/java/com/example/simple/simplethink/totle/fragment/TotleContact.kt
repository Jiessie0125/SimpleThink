package com.example.simple.simplethink.totle.fragment

import android.graphics.Bitmap
import com.example.simple.simplethink.model.TotleSortResponse

/**
 * Created by jiessie on 2019/6/5.
 */
interface TotleContact {

    interface  Presenter {
        fun getTotleSort()
        fun getItemImage(image: String)
    }

    interface View {
        fun getTotleSortIcon(list : List<TotleSortResponse>)
        fun getItemImage(image: Bitmap)
    }
}