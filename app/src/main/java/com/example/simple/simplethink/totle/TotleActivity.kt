package com.example.simple.simplethink.totle

import android.app.Activity
import android.os.Bundle
import com.example.simple.simplethink.R

/**
 * Created by mobileteam on 2019/6/3.
 */
class TotleActivity: Activity(){

    lateinit var persenter : TotleContact.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_totle)
    }

    fun init(){
        persenter = TotlePresenter()
        persenter.getTotleSort()
    }
}