package com.example.simple.simplethink.utils

import android.content.Context
import android.content.res.Resources

/**
 * Created by mobileteam on 2019/6/6.
 */
class ResourcesUtils private constructor(){

    init {
        throw UnsupportedOperationException("can't new ResourcesUtils")
    }

    companion object {
        private var context : Context ?=null

        fun getAppContext(): Context? {
            if (context != null) return context
            throw NullPointerException("u should init first")
        }

        val resource : Resources
            get() = getAppContext()!!.resources

        fun getString(resId: Int): String {
            return resource.getString(resId)
        }

        fun init(context: Context) {
            ResourcesUtils.context = context.getApplicationContext()
        }
    }
}