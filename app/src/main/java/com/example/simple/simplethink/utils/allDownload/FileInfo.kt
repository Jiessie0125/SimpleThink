package com.example.simple.simplethink.utils.allDownload

import java.io.Serializable
/**
 * Created by jiessie on 2019/9/8.
 */

class FileInfo : Serializable {
    var id: Int = 0
    var url: String? = null
    var fileName: String? = null
    var length: Int = 0

    constructor() {

    }

    constructor(id: Int, url: String, fileName: String, length: Int) {
        this.id = id
        this.url = url
        this.fileName = fileName
        this.length = length
    }

}
