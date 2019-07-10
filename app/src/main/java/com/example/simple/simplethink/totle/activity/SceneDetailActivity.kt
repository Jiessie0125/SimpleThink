package com.example.simple.simplethink.totle.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.simple.simplethink.R

/**
 * Created by jiessie on 2019/7/5.
 */
class SceneDetailActivity: AppCompatActivity()  {

    companion object {
        fun newIntent (context: Context?) : Intent {
            var intent = Intent(context, SceneDetailActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_scene_detail)
    }
}