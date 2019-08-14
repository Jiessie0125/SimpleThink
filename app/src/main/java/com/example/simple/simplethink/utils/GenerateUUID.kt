package com.example.simple.simplethink.utils

import java.util.*

/**
 * Created by jiessie on 2019/8/13.
 */
object GenerateUUID {
    fun generateOneUUID(): String{
        var id = UUID.randomUUID().toString()
        id=id.replace("-", "")
        return id
    }
}