package com.example.simple.simplethink.buzzy

import com.example.simple.simplethink.model.TotleItem

/**
 * Created by mobileteam on 2019/6/21.
 */
class BuzzyCourseContact {

    interface Presenter{
        fun getBuzzyCourse()
    }

    interface View{
        fun setBuzzyCourseAdapter(buzzyCourseUrlList: ArrayList<TotleItem>)
    }
}