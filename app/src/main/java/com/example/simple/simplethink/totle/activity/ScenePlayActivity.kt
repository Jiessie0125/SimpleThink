package com.example.simple.simplethink.totle.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.simple.simplethink.R
import kotlinx.android.synthetic.main.activity_scene_paly.*

/**
 * Created by jiessie on 2019/7/13.
 */
class ScenePlayActivity : AppCompatActivity(){

    companion object {
        const val SCENERESOURCE = "SCENERESOURCE"
        fun newIntent (context: Context?) : Intent {
            var intent = Intent(context, ScenePlayActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_paly)
        initView()
    }
    private fun initView(){
        scene_paly_close.setOnClickListener { finish() }
    }
}