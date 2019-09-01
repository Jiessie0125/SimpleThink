package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.*
import com.example.simple.simplethink.model.bean.CourseResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*
import java.io.File

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
    fun getSubscription(/*@Header("Authorization") authorization : String*/): Observable<SubscriptionResponse>

    @GET("/version/android")
    fun getAppVersion(): Observable<SubscriptionResponse>

    @DELETE("/user/session")
    fun appLogoff(): Observable<ResponseBody>
    @FormUrlEncoded
    @POST("/oauth/access_token")
    fun refresh(@FieldMap params: Map<String, String>): Observable<AuthResponse>

    @Multipart
    @POST("/file")
    fun uploadFile(@Part field: MultipartBody.Part): Observable<UploadFileResponse>

    @FormUrlEncoded
    @PUT("/user/info")
    fun updateUser(@FieldMap params: HashMap<String, String>):Observable<ResponseBody>

    @Multipart
    @POST("/course/logs")
    fun uploadPractice(@Part("course_logs") params:  RequestBody): Observable<ResponseBody>

    @GET("/course/logs")
    fun getCourseLogs(@Query("start_date")start_date:String, @Query("end_date")end_date:String): Observable<ResponseBody>

    @POST("/subscription/order")
    fun createSubscription(@Body params:  RequestBody): Observable<CreateSubscriptionResponse>

    @FormUrlEncoded
    @POST("/wx/wx_order")
    fun wxOrder(@Field("order_id") orderId: String): Observable<OrderWXResponse>

    @FormUrlEncoded
    @POST("/alipay/order_string")
    fun aliOrder(@Field("order_id")orderId: String): Observable<OrderAliPayResponse>

    @FormUrlEncoded
    @POST("/alipay/alipay_check_order")
    fun checkAliOrder(@Field("order_id")orderId: String?): Observable<PayResponse>

    @FormUrlEncoded
    @POST("/wx/wx_check_order")
    fun checkWechatOrder(@Field("order_id")orderId: String?): Observable<PayResponse>
}