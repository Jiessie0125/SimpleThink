package com.example.simple.simplethink.netapi

import android.graphics.Bitmap
import com.example.simple.simplethink.model.TotleSortListResponse
import com.example.simple.simplethink.model.TotleSortResponse
import io.reactivex.Observable

/**
 * Created by mobileteam on 2019/6/4.
 */
interface HttpRepository {
    fun getTotleSort(): Observable<List<TotleSortResponse>>
    fun getItemImage(url: String) : Observable<Bitmap>
}