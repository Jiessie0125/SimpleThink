package com.example.simple.simplethink.totle.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.ScenesResponse
import com.example.simple.simplethink.model.Sections
import com.example.simple.simplethink.totle.adapter.SceneDetailAdapter
import com.example.simple.simplethink.utils.ImageUtil.showBKImage
import kotlinx.android.synthetic.main.activity_scene_detail.*

/**
 * Created by jiessie on 2019/7/5.
 */
class SceneDetailActivity: AppCompatActivity()  {

    lateinit var sceneDetailAdapter : SceneDetailAdapter

    companion object {
        const val SCENEDETAIL = "SCENEDETAIL"
        fun newIntent (context: Context?, scenesResponse: ScenesResponse) : Intent {
            var intent = Intent(context, SceneDetailActivity::class.java)
            intent.putExtra(SCENEDETAIL,scenesResponse)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_detail)
        initView()
    }

    fun initView(){
        val intent = intent
        val sceneResponse = intent.getSerializableExtra(SCENEDETAIL) as ScenesResponse
        showBKImage(sceneResponse.content_img_new,this,scene_bg)
        scene_title.text = sceneResponse.title
        scene_detail_back.setOnClickListener { finish() }
        setSceneDetailAdapter(sceneResponse.sections)
    }

    private fun setSceneDetailAdapter(sections : List<Sections>){
        sceneDetailAdapter = SceneDetailAdapter(this,sections)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        scene_detail_rv.layoutManager = layoutManager
        scene_detail_rv.adapter = sceneDetailAdapter
    }
}