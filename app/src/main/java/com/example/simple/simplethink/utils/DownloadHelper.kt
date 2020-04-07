package com.example.simple.simplethink.utils

import android.os.AsyncTask
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

/**
 * Created by jiessie on 2019/7/14.
 */

internal object DownloadHelper {
    private var mTask : DownloadAsyncTask ?= null
    private var filePath : String ?=null
    fun download(url: String, localPath: String, listener: OnDownloadListener) {
        filePath = localPath
        mTask = DownloadAsyncTask(url, localPath, listener)
        mTask?.execute()
    }

    fun cancelDownload(){
        if(mTask != null && mTask?.status == AsyncTask.Status.RUNNING){
            FilesUtils.deleteFile(File(filePath))
            mTask?.cancel(true)
        }
    }

     class DownloadAsyncTask internal constructor(private val mUrl: String, private val mFilePath: String, private val mListener: OnDownloadListener?) : AsyncTask<String, Int, Boolean>() {
        private var mFailInfo: String? = null
        override fun doInBackground(vararg params: String): Boolean? {
            if(isCancelled){
                FilesUtils.deleteFile(File(mFilePath))
                return null
            }
            val pdfUrl = mUrl
            try {
                val url = URL(pdfUrl)
                val urlConnection = url.openConnection()
                val `in` = urlConnection.getInputStream()
                val contentLength = urlConnection.getContentLength()
                val pdfFile = File(mFilePath)
                if (pdfFile.exists()) {
                    val result = pdfFile.delete()
                    if (!result) {
                        mFailInfo = "存储路径下的同名文件删除失败！"
                        return false
                    }
                }
                var downloadSize = 0
                val bytes = ByteArray(1024)
                var length: Int
                val out = FileOutputStream(mFilePath)
                length = `in`.read(bytes)
                while ((length) != -1) {
                    out.write(bytes, 0, length)
                    downloadSize += length
                    publishProgress(downloadSize*100 / contentLength )
                    length = `in`.read(bytes)
                }
                `in`.close()
                out.close()
            } catch (e: IOException) {
                e.printStackTrace()
                mFailInfo = e.message
                return false
            }

            return true
        }

        override fun onPreExecute() {
            super.onPreExecute()
            mListener?.onStart()
        }

        override fun onPostExecute(aBoolean: Boolean?) {
            super.onPostExecute(aBoolean)
            if (mListener != null) {
                if (aBoolean!!) {
                    mListener.onSuccess(File(mFilePath))
                } else {
                    mListener.onFail(File(mFilePath), mFailInfo)
                }
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                if(isCancelled) {
                    FilesUtils.deleteFile(File(mFilePath))
                    return@onProgressUpdate
                }
                if (values.size > 0) {
                    var mProcess= values[0] ?: 0
                    mListener?.onProgress(mProcess)
                }
        }
        override fun onCancelled() {
            Log.e("--cancel any---","")
            mListener?.onProgress(0)
            FilesUtils.deleteFile(File(mFilePath))
        }
    }

     interface OnDownloadListener {
        fun onStart()
        fun onSuccess(file: File)
        fun onFail(file: File, failInfo: String?)
        fun onProgress(progress: Int?)
    }
}
