package com.example.simple.simplethink.netapi

import android.graphics.Bitmap
import com.example.simple.simplethink.model.TotleSortListResponse
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.network.RetrofitServiceManager
import io.reactivex.Observable

/**
 * Created by mobileteam on 2019/6/4.
 */
class HttpResposityImpl(): HttpRepository {
    override fun getTotleSort(): Observable<List<TotleSortResponse>> {
        val getTotleSortRetrofit = RetrofitServiceManager.instance.create(HttpRetrofitApiService::class.java)
        return getTotleSortRetrofit.getTotleSort()
    }

    override fun getItemImage(url: String): Observable<Bitmap> {
        val getItemImage= RetrofitServiceManager.instance.createRetrofit(HttpRetrofitApiService::class.java)
        return getItemImage.getItemImage(url)
    }
}