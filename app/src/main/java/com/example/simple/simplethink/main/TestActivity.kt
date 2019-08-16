package com.example.simple.simplethink.main

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.example.simple.simplethink.R
import com.example.simple.simplethink.utils.SharePicturePopupWindow
import kotlinx.android.synthetic.main.activity_test.*

/**
 * Created by mobileteam on 2019/8/15.
 */
class TestActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        test1.setOnClickListener {
            val test = SharePicturePopupWindow(this, "")
            test.showAtLocation(ad_popup1, Gravity.BOTTOM, 0, 0)
        }
    }
}