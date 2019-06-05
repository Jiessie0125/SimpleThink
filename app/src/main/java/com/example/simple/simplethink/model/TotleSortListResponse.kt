package com.example.simple.simplethink.model

import com.google.gson.reflect.TypeToken
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class TotleSortListResponse(
        val totleSortListResponse : TypeToken<List<TotleSortResponse>>
): Serializable