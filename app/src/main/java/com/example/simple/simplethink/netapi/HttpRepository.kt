package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.*
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Created by mobileteam on 2019/6/4.
 */
interface HttpRepository {
    fun getSplashBanner(): Observable<BannerResponse>
    fun getBanner(): Observable<List<BannerResponse>>
    fun getBannerView(url: String): Observable<ResponseBody>
    fun getTotleSort(): Observable<List<TotleSortResponse>>
    fun getItemImage(url: String) : Observable<ResponseBody>
    fun getCourseImage() : Observable<FirstCourseResponse>
    fun getCourseImageItem(url: String): Observable<ResponseBody>
    fun getBuzzyCourse(id: Int): Observable<List<BuzzyCourseResponse>>
    fun getScenes(): Observable<List<ScenesResponse>>
    fun getSceneMP3(url: String): Observable<ResponseBody>
    fun getWhiteItem(): Observable<List<WhiteNoiseItemResponse>>
}