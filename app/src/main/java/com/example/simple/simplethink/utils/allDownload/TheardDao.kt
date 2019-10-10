package com.example.simple.simplethink.utils.allDownload

/**
 * Created by jiessie on 2019/9/8.
 */

interface ThreadDao {

    val allThreads: List<ThreadInfo>

    //系统被杀死的时候没有完成的任务
    val allContinueThreads: List<ThreadInfo>

    //插入线程信息
    fun insertThread(threadInfo: ThreadInfo)

    //删除线程
    fun deleteThread(url: String, thread_id: Int)

    //更新线程信息
    fun updateThread(url: String, thread_id: Int, state: Int)


    //根据url查询文件的线程信息
    fun getThreadsByUrl(url: String): ThreadInfo

    //线程信息是否存在
    fun isExists(url: String, thread_id: Int): Boolean
}
