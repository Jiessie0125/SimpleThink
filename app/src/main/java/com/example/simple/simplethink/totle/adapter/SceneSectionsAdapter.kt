package com.example.simple.simplethink.totle.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.Sections
import com.example.simple.simplethink.model.TotleItem
import java.util.ArrayList

/**
 * Created by jiessie on 2019/6/11.
 */
class SceneSectionsAdapter(val totleList : List<Sections> ) : RecyclerView.Adapter<SceneSectionsViewHolder>() {


    override fun getItemCount(): Int {
        return totleList?.size!!
    }

    override fun onBindViewHolder(holder: SceneSectionsViewHolder?, position: Int) {
        holder?.mItemImage?.text = totleList?.get(position)?.title
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SceneSectionsViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.scene_sections_item, null, false)
        return SceneSectionsViewHolder(holder)
    }

}

class SceneSectionsViewHolder(view : View?): RecyclerView.ViewHolder(view) {
    var mItemImage : TextView?

    init {
        mItemImage = view?.findViewById(R.id.scene_sections_tv)
    }

}