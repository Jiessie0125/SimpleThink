package com.example.simple.simplethink.totle.activity.down

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.ScenesResponse
import com.example.simple.simplethink.model.Sections
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.activity.RecyclerViewSpacesItemDecoration
import com.example.simple.simplethink.totle.activity.SceneDetailContact
import com.example.simple.simplethink.totle.activity.SceneDetailPresenter
import com.example.simple.simplethink.totle.activity.ScenePlayActivity
import com.example.simple.simplethink.totle.adapter.DownloadItemAdapter
import com.example.simple.simplethink.totle.adapter.SceneDetailAdapter
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_download.*
import kotlinx.android.synthetic.main.activity_vip_center.*

/**
 * Created by jiessie on 2019/7/5.
 */
class DownloadActivity : AppCompatActivity() {
    lateinit var downloadAdapter : DownloadItemAdapter

    companion object {
        fun newIntent (context: Context?) : Intent {
            var intent = Intent(context, DownloadActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        initView()
    }

    fun initView(){
       // downloadAdapter = DownloadItemAdapter(this)
        vip_detail.layoutManager = LinearLayoutManager(this)
        downloadClass.adapter = downloadAdapter
        downloadAdapter.notifyDataSetChanged()
    }


}