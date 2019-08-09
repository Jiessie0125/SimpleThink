package com.example.simple.simplethink.totle.activity.down

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.totle.adapter.DownloadItemAdapter
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_download.*
import kotlinx.android.synthetic.main.title_tool.*

/**
 * Created by jiessie on 2019/7/5.
 */
class DownloadActivity : BaseActivity() {
    lateinit var downloadAdapter : DownloadItemAdapter
    var downloadArray = ArrayList<String>()

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
        setHeader(ResourcesUtils.getString(R.string.download_manager))
        val folder = this.getExternalFilesDirs(null)[0]
        folder?.let {
            it.list().forEach { item ->
                downloadArray.add(item)
            }
        }
        downloadAdapter = DownloadItemAdapter(this)
        downloadArray?.let {
            downloadClass.layoutManager =LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            downloadArray.forEach { item ->
                downloadAdapter.setData(downloadArray)
            }
            downloadClass.adapter = downloadAdapter
            downloadAdapter.setOnItemClickListener(object : DownloadItemAdapter.OnDownloadItemClickListener {
                override fun onItemClick(v: View?, position: Int) {
                    when (v?.getId()) {
                        R.id.download_delete_ry -> removeBigClass(downloadArray[position])
                        else ->showSmallClass(downloadArray[position])
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
           // folder.delete()
            FilesUtils.deleteFile(folder)
            downloadArray.remove(bigClassName)
            downloadAdapter.setData(downloadArray)
        }
    }
    private fun showSmallClass(bigClassName : String){
        val downloadSmallIntent = DownloadSmallActivity.newIntent(this,bigClassName)
        startActivity(downloadSmallIntent)
    }

}