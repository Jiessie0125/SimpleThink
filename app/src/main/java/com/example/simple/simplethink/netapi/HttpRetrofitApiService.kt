package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * Created by mobileteam on 2019/6/4.
 */
interface HttpRetrofitApiService {

    @GET("ad/get")
    fun getSplashBanner(): Observable<BannerResponse>

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

    @GET("new_course/get_exhibition/{exhibition_id}")
    fun getBuzzyCourse(@Path("exhibition_id")id:Int):  Observable<List<BuzzyCourseResponse>>

    @GET("scenes/get")
    fun getScenes(): Observable<List<ScenesResponse>>

    @GET
    fun getSceneMP3(@Url url:String):Observable<ResponseBody>

    @GET("white_noise/get")
    fun getWhiteItem(): Observable<List<WhiteNoiseItemResponse>>

    @GET("new_course/course/{category_id}")
    fun getSortCourse(@Path("category_id")id:Int):  Observable<List<BuzzyCourseResponse>>
}