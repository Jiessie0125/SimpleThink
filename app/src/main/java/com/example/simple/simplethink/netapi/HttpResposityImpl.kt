package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.*
import com.example.simple.simplethink.model.bean.CourseResponse
import com.example.simple.simplethink.network.GetRetrofitServiceManager
import com.example.simple.simplethink.network.RetrofitServiceManager
import com.example.simple.simplethink.utils.DateUtils
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Part
import java.io.File
import java.util.*

/**
 * Created by mobileteam on 2019/6/4.
 */
class HttpResposityImpl: HttpRepository {
    override fun getSplashBanner(): Observable<BannerResponse> {
        val getSplashBannerRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getSplashBannerRetrofit.getSplashBanner()
    }

    override fun getBanner(): Observable<List<BannerResponse>> {
        val getTotleSortRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getTotleSortRetrofit.getBanner()
    }
    override fun getBannerView(url: String): Observable<ResponseBody> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getBannerView(url)
    }

    override fun getTotleSort(): Observable<List<TotleSortResponse>> {
        val getTotleSortRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getTotleSortRetrofit.getTotleSort()
    }

    override fun getItemImage(url: String): Observable<ResponseBody> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getItemImage(url)
    }

    override fun getCourseImage(): Observable<FirstCourseResponse> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getCourseImage()
    }

    override fun getCourseImageItem(url: String): Observable<ResponseBody> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getCourseImageItem(url)
    }

    override fun getBuzzyCourse(id: Int): Observable<List<BuzzyCourseResponse>> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getBuzzyCourse(id)
    }

    override fun getScenes(): Observable<List<ScenesResponse>> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getScenes()
    }

    override fun getSceneMP3(url: String): Observable<ResponseBody> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getSceneMP3(url)
    }

    override fun getWhiteItem(): Observable<List<WhiteNoiseItemResponse>> {
        val getTotleSortRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getTotleSortRetrofit.getWhiteItem()
    }

    override fun getSortCourse(id: Int): Observable<List<BuzzyCourseResponse>> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getSortCourse(id)
    }
    override fun getCourseDetail(id: Int): Observable<CourseResponse> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getCourseDetail(id)
    }
    override fun sendSMS(phoneNumber: Long): Observable<ResponseBody> {
        val sendSMS = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        val params = HashMap<String, Long>().apply {
            put("phone_number", phoneNumber)
        }
        return sendSMS.sendSMS(params)
    }

    override fun updateUserInfo(password_old: String, password_new: String, username: String, code: String): Observable<ResponseBody> {
        val updateUserInfo = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        val params = HashMap<String, String>().apply {
            put("password_old", password_old)
            put("password_new", password_new)
            put("username", username)
            put("code", code)
        }
        return updateUserInfo.updateUserInfo(params)
    }
    override fun register(password: String, username: String, code: String, gender: String, nickname: String, avatar: String, rangeAge: String, rangeLocation: String): Observable<ResponseBody> {
        val register = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        val params = HashMap<String, String>().apply {
            put("password", password)
            put("username", username)
            put("platform", "android")
            put("gender", gender)
            put("code", code)
            put("nickname", nickname)
            put("avatar", avatar)
            put("range_age", rangeAge)
            put("range_location", rangeLocation)
        }
        return register.register(params)
    }

    override fun getSuggestedActivity(): Observable<List<ActivityResponse>> {
        val getSuggestedActivityRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getSuggestedActivityRetrofit.getSuggestedActivity()
    }

    override fun getBottomActivity(): Observable<BottomActivityResponse> {
        val getBottomActivityRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getBottomActivityRetrofit.getBottomActivity()
    }

    override fun getSuggestedCourse(): Observable<List<SuggestedCourse>> {
        val getSuggestedCourseRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getSuggestedCourseRetrofit.getSuggestedCourse();
    }

    override fun getSubscription(/*authorization: String*/): Observable<SubscriptionResponse> {
        val getSuggestedCourseRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getSuggestedCourseRetrofit.getSubscription();
    }

    override fun createSubscription(@Body params:  RequestBody): Observable<CreateSubscriptionResponse> {
        val getSuggestedCourseRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getSuggestedCourseRetrofit.createSubscription(params);
    }

    override fun wxOrder(orderId: String): Observable<OrderWXResponse> {
        val getSuggestedCourseRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getSuggestedCourseRetrofit.wxOrder(orderId);
    }

    override fun aliOrder(orderId: String): Observable<OrderAliPayResponse> {
        val getSuggestedCourseRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getSuggestedCourseRetrofit.aliOrder(orderId)
    }

    override fun checkAliOrder(orderId: String?): Observable<PayResponse> {
        val getSuggestedCourseRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getSuggestedCourseRetrofit.checkAliOrder(orderId)
    }

    override fun checkWechatOrder(orderId: String?): Observable<PayResponse> {
        val getSuggestedCourseRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getSuggestedCourseRetrofit.checkWechatOrder(orderId)
    }

    override fun refresh(refresh : String): Observable<AuthResponse> {
        val service = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        val params = HashMap<String, String>().apply {
            put("refresh_token", refresh)
            put("client_secret", "3f54abb72023eeb7f32370242ffce0926b73aa24")
            put("client_id", "android")
            put("grant_type", "refresh_token")
        }
        return service.refresh(params)
    }

    override fun appLogoff(): Observable<ResponseBody> {
        val appLogoffRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return appLogoffRetrofit.appLogoff()
    }

    override fun uploadFile(file: MultipartBody.Part): Observable<UploadFileResponse> {
        val uploadFileRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return uploadFileRetrofit.uploadFile(file)
    }

    override fun updateUser(file: String?, nikeName: String?): Observable<ResponseBody> {
        val service = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        val params = HashMap<String, String>().apply {
            file?.let {
                put("avatar", it)
            }
            nikeName?.let {
                put("nickname", it)
            }
        }
        return service.updateUser(params)
    }

    override fun uploadPractice(@Part params:  RequestBody): Observable<ResponseBody> {
        val appLogoffRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return appLogoffRetrofit.uploadPractice(params)
    }
    override fun getCourseLogs(): Observable<ResponseBody> {
        val getSuggestedCourseRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        val nowDate = DateUtils.dateToStr(Date())
        val monthAgoDate = DateUtils.getNextDay(nowDate,"-31")
        return getSuggestedCourseRetrofit.getCourseLogs(monthAgoDate, nowDate);
    }
}