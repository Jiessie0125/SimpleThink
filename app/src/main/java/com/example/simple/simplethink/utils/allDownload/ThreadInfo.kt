package com.example.simple.simplethink.utils.allDownload

/**
 * Created by jiessie on 2019/9/8.
 */

class ThreadInfo {
    var id: Int = 0
    var url: String? = null
    var filelength: Long = 0
    var state: Int = 0
    var title: String? = null

    constructor() {

    }

    constructor(id: Int, url: String, filelength: Long, state: Int, title: String) {
        this.id = id
        this.url = url
        this.filelength = filelength
        this.state = state
        this.title = title
    }
}
