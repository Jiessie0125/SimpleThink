package com.example.simple.simplethink.totle.fragment

import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl

/**
 * Created by jiessie on 2019/6/5.
 */
interface TotleContact {

    interface  Presenter {
         fun getTotleSort()
    }

    interface View {
        fun showTotleSort(list : List<TotleSortResponse>)
    }
}