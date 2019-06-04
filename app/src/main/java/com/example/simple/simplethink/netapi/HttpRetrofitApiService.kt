package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.TotleSortResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by mobileteam on 2019/6/4.
 */
interface HttpRetrofitApiService {
    @GET("http://dev.simplemeditation.cn/new_course/category")
    fun getTotleSort(): Observable<TotleSortResponse>
}