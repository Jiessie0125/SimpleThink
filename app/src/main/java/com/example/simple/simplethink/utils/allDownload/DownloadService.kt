package com.example.simple.simplethink.utils.allDownload

import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.support.annotation.Nullable
import java.io.File
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by jiessie on 2019/9/8.
 */

class DownloadService : Service() {
    private var downloadTask: DownloadTask? = null

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_INIT -> {
                    val fileInfo = msg.obj as FileInfo
                    //启动下载任务
                    downloadTask = DownloadTask(this@DownloadService, fileInfo)
                    downloadTask!!.download()
                    mTasks.put(fileInfo.id, downloadTask as DownloadTask)
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //获得Activity的参数
        if (ACTION_START == intent.getAction()) {
            val fileInfo = intent.getSerializableExtra("fileInfo") as FileInfo
            InitThread(fileInfo).start()
        } else if (ACTION_STOP == intent.getAction()) {
            val fileInfo = intent.getSerializableExtra("fileInfo") as FileInfo
            //从集合中取出下载任务
            val downloadTask = mTasks[fileInfo.id]
            if (downloadTask != null) {
                downloadTask.isPause = true
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    internal inner class InitThread(mFileInfo: FileInfo) : Thread() {
        private var mFileInfo: FileInfo? = null

        init {
            this.mFileInfo = mFileInfo
        }

        override fun run() {
            var httpURLConnection: HttpURLConnection? = null
            val raf: RandomAccessFile? = null
            try {
                val url = URL(mFileInfo!!.url)
                httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection!!.setConnectTimeout(3000)
                httpURLConnection!!.setRequestMethod("GET")
                var length = -1
                if (httpURLConnection!!.getResponseCode() === 200) {
                    length = httpURLConnection!!.getContentLength()
                }
                if (length <= 0) {
                    return
                }
                val dir = File(DOWNLOAD_PATH)
                if (!dir.exists()) {
                    dir.mkdir()
                }
                mFileInfo!!.length = length
                val message = handler.obtainMessage(MSG_INIT, mFileInfo)
                message.sendToTarget()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection!!.disconnect()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        val ACTION_START = "ACTION_START"
        val ACTION_STOP = "ACTION_STOP"
        val ACTION_UPDATA = "ACTION_UPDATA"
        val ACTION_ERRO = "ACTION_ERRO"
        val DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ADEMO"
        val MSG_INIT = 0
        //下载任务的集合
        var mTasks = LinkedHashMap<Int, DownloadTask>()
    }
}
