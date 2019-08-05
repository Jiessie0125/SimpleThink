package com.example.simple.simplethink.vip

import com.example.simple.simplethink.model.BuzzyCourseResponse

/**
 * Created by mobileteam on 2019/6/21.
 */
class VIPCenterContact {

    interface Presenter{
        fun getSubscription(auth : String)
    }

    interface View{
        fun updateVipItem(buzzyCourseUrlList : List<BuzzyCourseResponse>)
    }
}