package com.example.simple.simplethink.model

import android.graphics.Bitmap
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
class SceneItem(
        var sceneItemTxt: String,
        var sceneItemImage: Bitmap,
        var sections : List<Sections>
): Serializable