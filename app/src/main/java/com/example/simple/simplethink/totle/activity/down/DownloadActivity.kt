package com.example.simple.simplethink.totle.activity.down

import android.app.AlertDialog
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
import android.content.DialogInterface



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
            it.list().forEach { item ->initDownloadArr(item)
            }
        }
        downloadAdapter = DownloadItemAdapter(this)
        if(downloadArray.isNotEmpty()) {
            no_download_record.visibility = View.GONE
            downloadClass.visibility = View.VISIBLE
            downloadClass.layoutManager =LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            downloadArray?.forEach { item ->
                downloadAdapter.setData(downloadArray)
            }
            downloadClass.adapter = downloadAdapter
            downloadAdapter.setOnItemClickListener(object : DownloadItemAdapter.OnDownloadItemClickListener {
                override fun onItemClick(v: View?, position: Int) {
                    when (v?.getId()) {
                        R.id.download_delete_ry -> showDeleteDialog(downloadArray[position])
                        else ->showSmallClass(downloadArray[position])
                    }
                }
            })
        }
    }

    private fun initDownloadArr( item : String){
        if (this.getExternalFilesDir(item).list().size > 0){
            downloadArray.add(item)
        }
    }

    override fun setHeader(title: String) {
        super.setHeader(title)
        title_tool_id.text = title
        title_tool_back_all.setOnClickListener { finish() }
    }

    private fun removeBigClass(bigClassName : String){
        val folder = this.getExternalFilesDir(bigClassName)
        if (folder.exists()) {
           // folder.delete()
            FilesUtils.deleteFile(folder)
            downloadArray?.remove(bigClassName)
            downloadAdapter.setData(downloadArray)
        }
        val folders = this.getExternalFilesDirs(null)[0]
        folders?.let {
            if(it.list().size == 0){
                no_download_record.visibility = View.VISIBLE
                downloadClass.visibility = View.GONE
            }
        }
    }
    private fun showSmallClass(bigClassName : String?){
        val downloadSmallIntent = DownloadSmallActivity.newIntent(this,bigClassName)
        startActivity(downloadSmallIntent)
        finish()
    }

    private fun showDeleteDialog(bigClassName : String) {
        val builder = AlertDialog.Builder(this)
                .setTitle("是否要删除该课程及其下所有子课程？")
                .setPositiveButton("取消", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .setNegativeButton("确定", DialogInterface.OnClickListener { dialog, which ->
                    removeBigClass(bigClassName)
                })
        builder.create().show()
    }

}