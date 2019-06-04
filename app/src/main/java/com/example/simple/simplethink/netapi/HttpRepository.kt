package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.TotleSortResponse
import io.reactivex.Observable

/**
 * Created by mobileteam on 2019/6/4.
 */
interface HttpRepository {
    fun getTotleSort(): Observable<TotleSortResponse>
}