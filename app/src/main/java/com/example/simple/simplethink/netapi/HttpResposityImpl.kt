package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.BannerResponse
import com.example.simple.simplethink.model.BuzzyCourseResponse
import com.example.simple.simplethink.model.FirstCourseResponse
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.network.RetrofitServiceManager
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Created by mobileteam on 2019/6/4.
 */
class HttpResposityImpl(): HttpRepository {
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

    override fun getBuzzyCourse(): Observable<List<BuzzyCourseResponse>> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getBuzzyCourse()
    }
}