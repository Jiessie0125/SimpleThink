package com.example.simple.simplethink.totle.activity.down

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.totle.activity.ScenePlayActivity
import com.example.simple.simplethink.totle.adapter.DownloadItemAdapter
import com.example.simple.simplethink.totle.adapter.DownloadSmallItemAdapter
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.SharePicturePopupWindow
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_download.*
import kotlinx.android.synthetic.main.title_tool.*
import java.io.File

/**
 * Created by jiessie on 2019/7/5.
 */
class DownloadSmallActivity : BaseActivity() {
    lateinit var downloadAdapter : DownloadSmallItemAdapter
    var downloadArray = ArrayList<String>()
    var sourceName =""

    companion object {
        const val SOURCENAME = "SOURCENAME"
        const val REQUEST_CODE = 1024
        fun newIntent (context: Context?,sourceName : String?) : Intent {
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
        sourceName = intent.getSerializableExtra(SOURCENAME) as String
        setHeader(ResourcesUtils.getString(R.string.download_manager))
        val folder = this.getExternalFilesDirs(sourceName)[0]
        folder?.let {
            it.list().forEach { item ->
                downloadArray.add(item)
            }
        }
        downloadAdapter = DownloadSmallItemAdapter(this)
        downloadArray?.let {
            no_download_record.visibility = View.GONE
            downloadClass.visibility = View.VISIBLE
            downloadClass.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            downloadArray.forEach { item ->
                downloadAdapter.setData(downloadArray)
            }
            downloadClass.adapter = downloadAdapter
            downloadAdapter.notifyDataSetChanged()
            downloadAdapter.setOnItemClickListener(object : DownloadSmallItemAdapter.OnDownloadSmallItemClickListener {
                override fun onItemClick(v: View?, position: Int) {
                    when (v?.getId()) {
                        R.id.download_small_delete_ry -> showDeleteDialog(downloadArray[position],sourceName)
                        R.id.download_small_play_ry ->{
                            showPlayPage(downloadArray[position],FilesUtils.getLocalFileUrl(downloadArray[position],sourceName))
                        }
                    }
                }
            })
        }
    }

    override fun setHeader(title: String) {
        super.setHeader(title)
        title_tool_id.text = title
        title_tool_back_all.setOnClickListener { showDownloadPage() }
    }

    private fun showDownloadPage(){
        val intent = DownloadActivity.newIntent(this)
        startActivity(intent)
        finish()
    }
    private fun showDeleteDialog(bigClassName : String,parentPath: String) {
        val builder = AlertDialog.Builder(this)
                .setTitle("是否要删除该课程？")
                .setPositiveButton("取消", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .setNegativeButton("确定", DialogInterface.OnClickListener { dialog, which ->
                    removeBigClass(bigClassName,parentPath)
                })
        builder.create().show()

    }

    private fun removeBigClass(bigClassName : String,parentPath: String){
        val folder = this.getExternalFilesDir(parentPath)
        val appDir = File(folder, bigClassName)
        if (appDir.exists()) {
            FilesUtils.deleteFile(folder)
            downloadArray.remove(bigClassName)
            downloadAdapter.setData(downloadArray)
        }
        val folders = this.getExternalFilesDirs(sourceName)[0]
        folders?.let {
            if(it.list().size == 0){
                no_download_record.visibility = View.VISIBLE
                downloadClass.visibility = View.GONE
            }
        }
    }
    private fun showPlayPage(sceneName : String,sceneSource: String){
        val praticeSections = SharedPreferencesUtil.getGsonString(this,sceneName)
        val intent = ScenePlayActivity.newIntent(this,sceneName,sceneSource,null,praticeSections)
        startActivityForResult(intent, DownloadSmallActivity.REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val courseName = data?.getStringExtra("courseName")
        if(requestCode == DownloadSmallActivity.REQUEST_CODE && resultCode == ScenePlayActivity.RESULT_CODE){
            val picturePopupWindow = SharePicturePopupWindow(this, courseName!!)
            picturePopupWindow.showAtLocation(ad_popup_pic_course, Gravity.BOTTOM, 0, 0)
        }
    }
}