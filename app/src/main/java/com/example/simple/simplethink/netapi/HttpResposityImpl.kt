package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.network.RetrofitServiceManager
import io.reactivex.Observable

/**
 * Created by mobileteam on 2019/6/4.
 */
class HttpResposityImpl(): HttpRepository {
    override fun getTotleSort(): Observable<TotleSortResponse> {
        return RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java).getTotleSort()
    }
}