package com.example.simple.simplethink.netapi

import com.example.simple.simplethink.model.BannerResponse
import com.example.simple.simplethink.model.BuzzyCourseResponse
import com.example.simple.simplethink.model.FirstCourseResponse
import com.example.simple.simplethink.model.TotleSortResponse
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Created by mobileteam on 2019/6/4.
 */
interface HttpRepository {
    fun getBanner(): Observable<List<BannerResponse>>
    fun getBannerView(url: String): Observable<ResponseBody>
    fun getTotleSort(): Observable<List<TotleSortResponse>>
    fun getItemImage(url: String) : Observable<ResponseBody>
    fun getCourseImage() : Observable<FirstCourseResponse>
    fun getCourseImageItem(url: String): Observable<ResponseBody>
    fun getBuzzyCourse(): Observable<List<BuzzyCourseResponse>>
}