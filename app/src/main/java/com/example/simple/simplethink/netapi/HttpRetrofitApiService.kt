package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.*
import com.example.simple.simplethink.model.bean.CourseResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

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


    @GET("new_course/section/{course_id}")
    fun getCourseDetail(@Path("course_id")id:Int):  Observable<CourseResponse>

    @FormUrlEncoded
    @POST("sms/send")
    fun sendSMS(@FieldMap params: Map<String, Long>): Observable<ResponseBody>

    @FormUrlEncoded
    @PUT("user/password")
    fun updateUserInfo(@FieldMap params: HashMap<String, String>): Observable<ResponseBody>


    @FormUrlEncoded
    @POST("user")
    fun register(@FieldMap params: HashMap<String, String>): Observable<ResponseBody>

    @GET("activity/get")
    fun getSuggestedActivity(): Observable<List<ActivityResponse>>

    @GET("bottom_activity/get")
    fun getBottomActivity(): Observable<BottomActivityResponse>

    @GET("new_course/recommend")
    fun getSuggestedCourse(): Observable<List<SuggestedCourse>>

    @GET("/subscription")
    fun getSubscription(@Header("Authorization") authorization : String): Observable<SubscriptionResponse>

}