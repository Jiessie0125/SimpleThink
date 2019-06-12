package com.example.simple.simplethink.model

import android.graphics.Bitmap
import com.google.gson.reflect.TypeToken
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
class TotleItem(
        val totleItemTxt: String,
        val totleItemImage: Bitmap
): Serializable