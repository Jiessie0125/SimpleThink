package com.example.simple.simplethink.totle.adapter

import android.app.Activity
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
import com.example.simple.simplethink.utils.FilesUtils
import java.util.ArrayList

/**
 * Created by jiessie on 2019/6/11.
 */
class SceneAdapter(val context: Activity,val sceneList : List<ScenesResponse>) : RecyclerView.Adapter<SceneViewHolder>() {

    var totleLish = ArrayList<SceneItem>()
    private var mClickListener : OnItemClickListener ?= null


    override fun getItemCount(): Int {
        return sceneList?.size!!
    }

    override fun onBindViewHolder(holder: SceneViewHolder?, position: Int) {
        var sceneSectionsAdapter = SceneSectionsAdapter(sceneList?.get(position)?.sections)
        holder?.mrecyclerView?.setHasFixedSize(true)
        holder?.mrecyclerView?.layoutManager= LinearLayoutManager(context)
        holder?.mrecyclerView?.adapter = sceneSectionsAdapter
        holder?.mTextView?.setText(sceneList?.get(position)?.title)
        //holder?.mItemImage?.setImageBitmap(totleLish?.get(position)?.sceneItemImage)
        FilesUtils.showImage(sceneList?.get(position)?.title_img_new,context,holder?.mItemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SceneViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.scene_item, null, false)
        return SceneViewHolder(holder,mClickListener)
    }

    fun setData(totleList : ArrayList<SceneItem>){
        totleLish = totleList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener( listener : OnItemClickListener){
        this.mClickListener = listener
    }
}

class SceneViewHolder(view: View, private val mListener: OnItemClickListener?) : RecyclerView.ViewHolder(view), View.OnClickListener {
    var mItemImage : ImageView?
    var mTextView : TextView?
    var mrecyclerView : RecyclerView?


    init {
        mItemImage = view?.findViewById(R.id.scene_img_rv)
        mrecyclerView = view?.findViewById(R.id.scene_sections)
        mTextView = view?.findViewById(R.id.scene_img_txt_rv)

        view.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        mListener?.onItemClick(v, getPosition())
    }

}
interface OnItemClickListener {
    fun onItemClick(view: View, postion: Int)
}