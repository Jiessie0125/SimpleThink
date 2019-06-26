package com.example.simple.simplethink.base

import android.app.Activity
import android.os.Bundle

/**
 * Created by jiessie on 2019/5/28.
 */
abstract  class BaseActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    open fun setHeader(){}
}