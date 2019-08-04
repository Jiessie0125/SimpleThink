package com.example.simple.simplethink.totle.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.ScenesResponse
import com.example.simple.simplethink.model.Sections
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.adapter.SceneDetailAdapter
import com.example.simple.simplethink.utils.DownloadHelper
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ImageUtil.showBKImage
import kotlinx.android.synthetic.main.activity_scene_detail.*
import kotlinx.android.synthetic.main.scene_detail_item.*
import java.io.File

/**
 * Created by jiessie on 2019/7/5.
 */
class SceneDetailActivity: AppCompatActivity() ,SceneDetailContact.View {

    lateinit var sceneDetailAdapter : SceneDetailAdapter
    lateinit var persenter: SceneDetailContact.Presenter
    private var isManager =false
    lateinit var  sceneResponse : ScenesResponse

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
        val httpResposityImpl = HttpResposityImpl()
        persenter = SceneDetailPresenter(httpResposityImpl, this)
        val intent = intent
        sceneResponse = intent.getSerializableExtra(SCENEDETAIL) as ScenesResponse
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
        val stringIntegerHashMap = HashMap< String, Int>()
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 25)//右间距
        scene_detail_rv.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
        scene_detail_rv.adapter = sceneDetailAdapter
        sceneDetailAdapter.setOnItemClickListener(object : SceneDetailAdapter.OnItemDetailClickListener {
            override fun onItemClick(v: View, viewName: SceneDetailAdapter.ViewName, position: Int) {
                when (v.getId()) {
                    R.id.scene_download -> downloadMP3(position)
                    else -> showSceneResourcePage(sections[position].title, FilesUtils.getLocalFileUrl(sections[position].title,SceneDetailAdapter.SCENEDETAIL),sceneResponse.content_img_new)
                }
            }
        })
    }

    private fun showSceneResourcePage(sceneName : String, sceneSource : String, bkground: String){
        val intent = ScenePlayActivity.newIntent(this,sceneName,sceneSource,bkground)
        startActivity(intent)
    }

    fun downloadMP3(position: Int){
        isManager = !isManager
        sceneDetailAdapter.changetShowDelImage(isManager ,position)
    }
    override fun updateUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}