package com.example.simple.simplethink.utils.allDownload

import android.content.Context

/**
 * Created by jiessie on 2019/9/8.
 */

class ThreadDaoImpl(context: Context) : ThreadDao {
    private var mHelper: DBHelper? = null

    override val allThreads: List<ThreadInfo>
        get() {
            val db = mHelper!!.writableDatabase
            val cursor = db.rawQuery("select * from thread_info", arrayOf())
            var threadInfo: ThreadInfo? = null
            val list = ArrayList<ThreadInfo>()
            while (cursor.moveToNext()) {
                threadInfo = ThreadInfo()
                threadInfo!!.id = cursor.getInt(cursor.getColumnIndex("thread_id"))
                threadInfo!!.url = cursor.getString(cursor.getColumnIndex("url"))
                threadInfo!!.filelength = cursor.getInt(cursor.getColumnIndex("filelength")).toLong()
                threadInfo!!.state = cursor.getInt(cursor.getColumnIndex("state"))
                threadInfo!!.title = cursor.getString(cursor.getColumnIndex("title"))
                list.add(threadInfo)
            }
            cursor.close()
            db.close()
            return list
        }

    override val allContinueThreads: List<ThreadInfo>
        get() {
            val db = mHelper!!.writableDatabase
            val cursor = db.rawQuery("select * from thread_info where state = ?", arrayOf("1"))
            var threadInfo: ThreadInfo? = null
            val list = ArrayList<ThreadInfo>()
            while (cursor.moveToNext()) {
                threadInfo = ThreadInfo()
                threadInfo!!.id = cursor.getInt(cursor.getColumnIndex("thread_id"))
                threadInfo!!.url = cursor.getString(cursor.getColumnIndex("url"))
                threadInfo!!.filelength = cursor.getInt(cursor.getColumnIndex("filelength")).toLong()
                threadInfo!!.state = cursor.getInt(cursor.getColumnIndex("state"))
                threadInfo!!.title = cursor.getString(cursor.getColumnIndex("title"))
                list.add(threadInfo)
            }
            cursor.close()
            db.close()
            return list
        }

    init {
        mHelper = DBHelper(context)
    }

    @Synchronized
    override fun insertThread(threadInfo: ThreadInfo) {
        val db = mHelper!!.writableDatabase
        db.execSQL("insert into thread_info(thread_id,url,filelength,state,title) values (?,?,?,?,?)",
                arrayOf(threadInfo.id, threadInfo.url, threadInfo.filelength, threadInfo.state, threadInfo.title))
        db.close()
    }

    @Synchronized override fun deleteThread(url: String, thread_id: Int) {
        val db = mHelper!!.writableDatabase
        db.execSQL("delete from thread_info where thread_id = ? and url = ?",
                arrayOf(thread_id, url))
        db.close()
    }

    @Synchronized override fun updateThread(url: String, thread_id: Int, state: Int) {
        val db = mHelper!!.writableDatabase
        db.execSQL("update thread_info set  state = ? where thread_id = ? and url = ?",
                arrayOf(state, thread_id, url))
        db.close()
    }

    @Synchronized override fun getThreadsByUrl(url: String): ThreadInfo {
        val db = mHelper!!.writableDatabase
        val cursor = db.rawQuery("select * from thread_info where url = ?", arrayOf(url))
        var threadInfo: ThreadInfo? = null
        while (cursor.moveToNext()) {
            threadInfo = ThreadInfo()
            threadInfo!!.id = cursor.getInt(cursor.getColumnIndex("thread_id"))
            threadInfo!!.url = cursor.getString(cursor.getColumnIndex("url"))
            threadInfo!!.filelength = cursor.getInt(cursor.getColumnIndex("filelength")).toLong()
            threadInfo!!.state = cursor.getInt(cursor.getColumnIndex("state"))
            threadInfo!!.title = cursor.getString(cursor.getColumnIndex("title"))
        }
        cursor.close()
        db.close()
        return threadInfo!!
    }

    @Synchronized override fun isExists(url: String, thread_id: Int): Boolean {
        val db = mHelper!!.writableDatabase
        val cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?",
                arrayOf(url, thread_id.toString() + ""))
        while (cursor.moveToNext()) {
            cursor.close()
            db.close()
            return true
        }
        return false
    }
}
