package com.example.simple.simplethink.totle.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.Sections
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.utils.FilesUtils
import java.util.ArrayList

/**
 * Created by jiessie on 2019/6/11.
 */
class SceneDetailAdapter( val context: Activity,val sections : List<Sections>) : RecyclerView.Adapter<SceneDetailHolder>() {


    override fun getItemCount(): Int {
        return sections?.size!!
    }

    override fun onBindViewHolder(holder: SceneDetailHolder?, position: Int) {
        holder?.mTotleItem?.text = sections?.get(position)?.title
        FilesUtils.showImage(sections?.get(position)?.title_img, context, holder?.mItemImage)
       /* holder?.mItemImage?.setImageBitmap(totleLish?.get(position)?.totleItemImage)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SceneDetailHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.scene_detail_item, null, false)
        return SceneDetailHolder(holder)
    }

}

class SceneDetailHolder(view : View?): RecyclerView.ViewHolder(view) {
    var mTotleItem : TextView?
    var mItemImage : ImageView?

    init {
        mTotleItem = view?.findViewById(R.id.recycle_scene_detail_tv)
        mItemImage = view?.findViewById(R.id.recycle_scene_detail_img)
    }

}