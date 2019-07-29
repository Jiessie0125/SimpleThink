package com.example.simple.simplethink.totle.activity.course

import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.model.bean.CourseResponse

/**
 * Created by mobileteam on 2019/6/21.
 */
class CourseDetailContact {

    interface Presenter{
        fun getCourse(id :Int)
    }

    interface View{
        fun setCourseAdapter(courseResponse : CourseResponse)
    }
}