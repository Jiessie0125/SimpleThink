package com.example.simple.simplethink.utils.allDownload

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by jiessie on 2019/9/8.
 */

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

    override  fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DROP)
        db.execSQL(SQL_CREATE)
    }

    companion object {
        private val DB_NAME = "download.db"
        private val VERSION = 1
        //state 1 是正在下载 2 是暂停  3是已经完成
        //thread_id(与文件id相同) 下载url 文件总长度 下载状态 title文件标题
        private val SQL_CREATE = "create table if not exists thread_info(" + "thread_id integer,url text,filelength integer,state integer,title text)"

        private val SQL_DROP = "drop table if exists thread_info"
    }
}