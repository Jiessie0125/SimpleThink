package com.example.simple.simplethink.totle.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.example.simple.simplethink.MyApp.Companion.context
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.model.PraticeSections
import com.example.simple.simplethink.model.ScenesResponse
import com.example.simple.simplethink.model.Sections
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.adapter.SceneDetailAdapter
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ImageUtil.showBKImage
import com.example.simple.simplethink.utils.SharePicturePopupWindow
import com.example.simple.simplethink.vip.VIPCenterActivity
import kotlinx.android.synthetic.main.activity_scene_detail.*

/**
 * Created by jiessie on 2019/7/5.
 */
class SceneDetailActivity: BaseActivity() ,SceneDetailContact.View {

    lateinit var sceneDetailAdapter : SceneDetailAdapter
    lateinit var persenter: SceneDetailContact.Presenter
    private var isManager =false
    lateinit var  sceneResponse : ScenesResponse

    companion object {
        const val REQUEST_CODE = 2
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
        setSceneDetailAdapter(sceneResponse.sections,sceneResponse.title)
    }

    private fun setSceneDetailAdapter(sections : List<Sections>,title : String){
        sceneDetailAdapter = SceneDetailAdapter(this,sections,title)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        scene_detail_rv.layoutManager = layoutManager
        val stringIntegerHashMap = HashMap< String, Int>()
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 25)//右间距
        scene_detail_rv.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
        scene_detail_rv.adapter = sceneDetailAdapter
        sceneDetailAdapter.setOnItemClickListener(object : SceneDetailAdapter.OnItemDetailClickListener {
            override fun onItemClick(v: View, viewName: SceneDetailAdapter.ViewName, position: Int) {
                val pratice = PraticeSections(sections[position].id,sections[position].course_id,sections[position].audio_id)
                when (v.getId()) {
                    R.id.scene_download ->  {if(sections[position].free == "true"){downloadMP3(position,pratice)}
                                                else {showVipPage()}}
                    else -> {
                        if(FilesUtils.isHaveFile(sections?.get(position)?.title, title)) {
                                showSceneResourcePage(sections[position].title,
                                FilesUtils.getLocalFileUrl(sections[position].title,title),
                                sceneResponse.content_img_new,
                                pratice)
                            }else{
                                val folder = context?.getExternalFilesDir(title)
                                if (folder?.exists()!!) {
                                    FilesUtils.deleteFile(folder)
                                }
                                if(sections[position].free == "false"){
                                    showVipPage()
                                }
                    }
                    }
                }
            }
        })
    }


    private fun showVipPage(){
        val intent = VIPCenterActivity.newIntent(this)
        startActivity(intent)
    }

    private fun showSceneResourcePage(sceneName : String, sceneSource : String, bkground: String,sections:PraticeSections){
        val intent = ScenePlayActivity.newIntent(this,sceneName,sceneSource,bkground,sections)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val courseName = data?.getStringExtra("courseName")
        if(requestCode == REQUEST_CODE && resultCode == ScenePlayActivity.RESULT_CODE){
            val picturePopupWindow = SharePicturePopupWindow(this, courseName!!)
            picturePopupWindow.showAtLocation(ad_popup_pic_scene, Gravity.BOTTOM, 0, 0)
        }
    }

    fun downloadMP3(position: Int,pratice : PraticeSections){
        isManager = !isManager
        sceneDetailAdapter.changetShowDelImage(isManager ,position,pratice)
    }
    override fun updateUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}