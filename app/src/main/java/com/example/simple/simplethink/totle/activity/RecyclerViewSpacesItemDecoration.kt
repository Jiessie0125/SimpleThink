package com.example.simple.simplethink.totle.activity

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by jiessie on 2019/7/13.
 */

class RecyclerViewSpacesItemDecoration(private val mSpaceValueMap: HashMap<String, Int>) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View,
                       parent: RecyclerView, state: RecyclerView.State) {
        if (mSpaceValueMap[TOP_DECORATION] != null)
            outRect?.top = mSpaceValueMap[TOP_DECORATION]
        if (mSpaceValueMap[LEFT_DECORATION] != null)

            outRect?.left = mSpaceValueMap[LEFT_DECORATION]
        if (mSpaceValueMap[RIGHT_DECORATION] != null)
            outRect?.right = mSpaceValueMap[RIGHT_DECORATION]
        if (mSpaceValueMap[BOTTOM_DECORATION] != null)

            outRect?.bottom = mSpaceValueMap[BOTTOM_DECORATION]

    }

    companion object {

        val TOP_DECORATION = "top_decoration"
        val BOTTOM_DECORATION = "bottom_decoration"
        val LEFT_DECORATION = "left_decoration"
        val RIGHT_DECORATION = "right_decoration"
    }


}
