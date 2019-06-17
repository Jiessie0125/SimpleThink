package com.example.simple.simplethink.netapi

import android.graphics.Bitmap
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.network.RetrofitServiceManager
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Created by mobileteam on 2019/6/4.
 */
class HttpResposityImpl(): HttpRepository {
    override fun getTotleSort(): Observable<List<TotleSortResponse>> {
        val getTotleSortRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getTotleSortRetrofit.getTotleSort()
    }

    override fun getItemImage(url: String): Observable<ResponseBody> {
        val getItemImage= RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getItemImage.getItemImage(url)
    }
}