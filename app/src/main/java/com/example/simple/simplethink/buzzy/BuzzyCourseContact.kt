package com.example.simple.simplethink.buzzy

import com.example.simple.simplethink.model.BuzzyCourseResponse
import com.example.simple.simplethink.model.TotleItem

/**
 * Created by mobileteam on 2019/6/21.
 */
class BuzzyCourseContact {

    interface Presenter{
        fun getBuzzyCourse(id :Int)
        fun getSortCouse(id: Int)
    }

    interface View{
        fun setBuzzyCourseAdapter(buzzyCourseUrlList : List<BuzzyCourseResponse>)
    }
}