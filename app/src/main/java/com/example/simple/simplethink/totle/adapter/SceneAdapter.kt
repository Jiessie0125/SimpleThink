package com.example.simple.simplethink.totle.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.SceneItem
import com.example.simple.simplethink.model.ScenesResponse
import com.example.simple.simplethink.model.TotleItem
import java.util.ArrayList

/**
 * Created by jiessie on 2019/6/11.
 */
class SceneAdapter(val context: Context) : RecyclerView.Adapter<SceneViewHolder>() {

    var totleLish = ArrayList<SceneItem>()


    override fun getItemCount(): Int {
        return totleLish?.size!!
    }

    override fun onBindViewHolder(holder: SceneViewHolder?, position: Int) {
        var sceneSectionsAdapter = SceneSectionsAdapter(totleLish?.get(position)?.sections)
        holder?.mrecyclerView?.setHasFixedSize(true)
        holder?.mrecyclerView?.layoutManager= LinearLayoutManager(context)
        holder?.mrecyclerView?.adapter = sceneSectionsAdapter
        holder?.mTextView?.setText(totleLish?.get(position)?.sceneItemTxt)
        holder?.mItemImage?.setImageBitmap(totleLish?.get(position)?.sceneItemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SceneViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.scene_item, null, false)
        return SceneViewHolder(holder)
    }

    fun setData(totleList : ArrayList<SceneItem>){
        totleLish = totleList
        notifyDataSetChanged()
    }
}

class SceneViewHolder(view : View?): RecyclerView.ViewHolder(view) {
    var mItemImage : ImageView?
    var mTextView : TextView?
    var mrecyclerView : RecyclerView?

    init {
        mItemImage = view?.findViewById(R.id.scene_img_rv)
        mrecyclerView = view?.findViewById(R.id.scene_sections)
        mTextView = view?.findViewById(R.id.scene_img_txt_rv)
    }

}