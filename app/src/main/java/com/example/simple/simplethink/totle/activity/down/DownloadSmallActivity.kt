package com.example.simple.simplethink.totle.activity.down

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.totle.activity.ScenePlayActivity
import com.example.simple.simplethink.totle.adapter.DownloadItemAdapter
import com.example.simple.simplethink.totle.adapter.DownloadSmallItemAdapter
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_download.*
import kotlinx.android.synthetic.main.title_tool.*

/**
 * Created by jiessie on 2019/7/5.
 */
class DownloadSmallActivity : BaseActivity() {
    lateinit var downloadAdapter : DownloadSmallItemAdapter
    var downloadArray = ArrayList<String>()

    companion object {
        const val SOURCENAME = "SOURCENAME"
        fun newIntent (context: Context?,sourceName : String) : Intent {
            var intent = Intent(context, DownloadSmallActivity::class.java)
            intent.putExtra(SOURCENAME,sourceName)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        initView()
    }

    fun initView(){
        var intent = getIntent()
        var sourceName = intent.getSerializableExtra(SOURCENAME) as String
        setHeader(ResourcesUtils.getString(R.string.download_manager))
        val folder = this.getExternalFilesDirs(sourceName)[0]
        folder?.let {
            it.list().forEach { item ->
                downloadArray.add(item)
            }
        }
        downloadAdapter = DownloadSmallItemAdapter(this)
        downloadArray?.let {
            downloadClass.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            downloadArray.forEach { item ->
                downloadAdapter.setData(downloadArray)
            }
            downloadClass.adapter = downloadAdapter
            downloadAdapter.notifyDataSetChanged()
            downloadAdapter.setOnItemClickListener(object : DownloadSmallItemAdapter.OnDownloadSmallItemClickListener {
                override fun onItemClick(v: View?, position: Int) {
                    when (v?.getId()) {
                        R.id.download_small_delete_ry -> removeBigClass(downloadArray[position])
                        R.id.download_small_play_ry ->showPlayPage(downloadArray[position],FilesUtils.getLocalFileUrl(downloadArray[position],sourceName))
                    }
                }
            })
        }
    }

    override fun setHeader(title: String) {
        super.setHeader(title)
        title_tool_id.text = title
        title_tool_back.setOnClickListener { finish() }
    }

    private fun removeBigClass(bigClassName : String){
        val folder = this.getExternalFilesDir(bigClassName)
        if (folder.exists()) {
            FilesUtils.deleteFile(folder)
            downloadArray.remove(bigClassName)
            downloadAdapter.setData(downloadArray)
        }
    }
    private fun showPlayPage(sceneName : String,sceneSource: String){
        val intent = ScenePlayActivity.newIntent(this,sceneName,sceneSource,null)
        startActivity(intent)
    }
}