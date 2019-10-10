package com.example.simple.simplethink.utils.allDownload

import android.content.Context
import android.content.Intent
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by jiessie on 2019/9/8.
 */

class DownloadTask(context: Context, fileInfo: FileInfo) {
    private var context: Context? = null
    private var fileInfo: FileInfo
    private var dao: ThreadDao? = null
    var isPause = false

    init {
        this.context = context
        this.fileInfo = fileInfo
        dao = ThreadDaoImpl(context)
    }

    fun download() {
        //读取数据库的线程任务信息
        var threadInfo: ThreadInfo? = dao!!.getThreadsByUrl(fileInfo!!.url!!)
        if (threadInfo == null) {
            //如果是空的初始化线程任务对象
            threadInfo = ThreadInfo(fileInfo.id, fileInfo.url!!, fileInfo.length.toLong(), 1, fileInfo.fileName!!)
        }
        //创建子线程开始下载
        DownloadThread(threadInfo).start()
    }

    //下载线程
    internal inner class DownloadThread(threadInfo: ThreadInfo) : Thread() {
        var threadInfo: ThreadInfo? = null
        private lateinit var file: File

        init {
            this.threadInfo = threadInfo
        }

        override fun run() {
            super.run()
            //向数据库插入线程任务信息
            if (!dao!!.isExists(threadInfo!!.url!!, threadInfo!!.id)) {
                //之前不存在这个线程任务就插入到数据库
                dao!!.insertThread(threadInfo!!)
            }
            var httpURLConnection: HttpURLConnection? = null
            var raf: RandomAccessFile? = null
            var inputStream: InputStream? = null
            try {
                val intent = Intent(DownloadService.ACTION_UPDATA)

                //设置文件写入位置 同时判断手机中是否已经有下载的相同文件
                file = File(DownloadService.DOWNLOAD_PATH, fileInfo!!.fileName)

                if (file.length().equals(fileInfo!!.length)) {
                    //已经有这个文件了
                    //下载完成之后发送广播
                    intent.putExtra("finished", 100.toString() + "")
                    intent.putExtra("fileId", fileInfo!!.id)
                    context!!.sendBroadcast(intent)
                    //下载完成之后更新线程任务信息
                    dao!!.updateThread(threadInfo!!.url!!, threadInfo!!.id, 3)
                    return
                }

                raf = RandomAccessFile(file, "rwd")
                raf!!.seek(file!!.length())

                //开始下载
                val url = URL(threadInfo!!.url)
                httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection!!.setConnectTimeout(3000)
                httpURLConnection!!.setRequestMethod("GET")
                httpURLConnection!!.setRequestProperty("RANGE", "bytes=" + file!!.length() + "-")
                inputStream = httpURLConnection!!.getInputStream()
                val bytes = ByteArray(1024 * 10)
                var len: Int
                var time = System.currentTimeMillis()
                while (inputStream!!.read(bytes) != -1) {
                    len  = inputStream!!.read(bytes)
                    //写入文件
                    raf!!.write(bytes, 0, len)
                    //下载进度发送广播给activity
                    if (System.currentTimeMillis() - time > 300) {
                        time = System.currentTimeMillis()
                        intent.putExtra("finished", (file!!.length() * 100 / fileInfo!!.length).toString() + "")
                        intent.putExtra("fileId", fileInfo.id)
                        context!!.sendBroadcast(intent)
                    }
                    //下载暂停保存下载进度
                    if (isPause) {
                        dao!!.updateThread(threadInfo!!.url!!, threadInfo!!.id, 2)
                        return
                    }
                }
                //下载完成之后发送广播
                intent.putExtra("finished", 100.toString() + "")
                intent.putExtra("fileId", fileInfo!!.id)
                context!!.sendBroadcast(intent)
                //下载完成之后更新线程任务信息
                dao!!.updateThread(threadInfo!!.url!!, threadInfo!!.id, 3)
            } catch (e: Exception) {
                e.printStackTrace()
                isPause = true
                dao!!.updateThread(threadInfo!!.url!!, threadInfo!!.id, 2)
                val intent = Intent(DownloadService.ACTION_ERRO)
                intent.putExtra("fileId", fileInfo!!.id)
                context!!.sendBroadcast(intent)
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection!!.disconnect()
                }
                if (raf != null) {
                    try {
                        raf!!.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                if (inputStream != null) {
                    try {
                        inputStream!!.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }


    }
}