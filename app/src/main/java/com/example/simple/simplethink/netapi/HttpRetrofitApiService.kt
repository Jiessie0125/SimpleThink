package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.TotleSortListResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by mobileteam on 2019/6/4.
 */
interface HttpRetrofitApiService {
    @GET("new_course/category")
    fun getTotleSort(): Observable<TotleSortListResponse>
}