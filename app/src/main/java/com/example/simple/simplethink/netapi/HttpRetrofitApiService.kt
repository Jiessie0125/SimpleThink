package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.BannerResponse
import com.example.simple.simplethink.model.BuzzyCourseResponse
import com.example.simple.simplethink.model.FirstCourseResponse
import com.example.simple.simplethink.model.TotleSortResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by mobileteam on 2019/6/4.
 */
interface HttpRetrofitApiService {
    @GET("banner/get")
    fun getBanner(): Observable<List<BannerResponse>>

    @GET
    fun getBannerView(@Url url:String):Observable<ResponseBody>

    @GET("new_course/category")
    fun getTotleSort(): Observable<List<TotleSortResponse>>

    @GET
    fun getItemImage(@Url url:String):Observable<ResponseBody>

    @GET("new_course/exhibition")
    fun getCourseImage(): Observable<FirstCourseResponse>

    @GET
    fun getCourseImageItem(@Url url:String):Observable<ResponseBody>

    @GET("banner/get")
    fun getBuzzyCourse():  Observable<List<BuzzyCourseResponse>>
}