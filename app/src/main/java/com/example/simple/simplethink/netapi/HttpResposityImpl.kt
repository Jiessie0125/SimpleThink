package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.*
import com.example.simple.simplethink.network.RetrofitServiceManager
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Created by mobileteam on 2019/6/4.
 */
class HttpResposityImpl(): HttpRepository {
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

}