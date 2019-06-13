package com.example.simple.simplethink.netapi

import android.graphics.Bitmap
import com.example.simple.simplethink.model.TotleSortListResponse
import com.example.simple.simplethink.model.TotleSortResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by mobileteam on 2019/6/4.
 */
interface HttpRetrofitApiService {
    @GET("new_course/category")
    fun getTotleSort(): Observable<List<TotleSortResponse>>

    @GET
    fun getItemImage(@Url url:String):Observable<Bitmap>
}