package com.example.simple.simplethink.totle.adapter

import android.app.Activity
import android.media.MediaPlayer
import android.os.Environment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.R.id.scene_download_mp3
import com.example.simple.simplethink.model.PraticeSections
import com.example.simple.simplethink.model.Sections
import com.example.simple.simplethink.totle.activity.view.RoundProgressBar
import com.example.simple.simplethink.utils.DownloadHelper
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.FilesUtils.timeParse
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import java.io.File

/**
 * Created by jiessie on 2019/6/11.
 */
class SceneDetailAdapter( val context: Activity,val sections : List<Sections>,val title : String) : RecyclerView.Adapter<SceneDetailAdapter.SceneDetailHolder>(), View.OnClickListener {

    private var mClickListener : OnItemDetailClickListener ?= null
    private var isShow = false
    private var mPosition = -1
    private var mPratice : PraticeSections ?=null

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun onBindViewHolder(holder: SceneDetailHolder?, position: Int) {
        holder?.mLinearLayout?.tag = position
        holder?.mTotleItem?.text = sections?.get(position)?.title
        FilesUtils.showImage(sections?.get(position)?.title_img, context, holder?.mItemImage)
        if(sections[position]?.free == "false"){
            holder?.mSceneVip?.visibility = View.VISIBLE
        }
        if(FilesUtils.isHaveFile(sections?.get(position)?.title, title)){
            holder?.mScenePlay?.visibility = View.VISIBLE
            holder?.mDownloadImage?.visibility = View.GONE
            holder?.mProcessBar?.visibility = View.GONE
        } else if(isShow && mPosition == position){
            holder?.mProcessBar?.visibility = View.VISIBLE
            holder?.mDownloadImage?.visibility = View.GONE
            updateProcessBar(sections[position].url,sections[position].title,holder?.mProcessBar)
        }else{
            holder?.mProcessBar?.visibility = View.GONE
            holder?.mDownloadImage?.tag = position
        }
        holder
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SceneDetailHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.scene_detail_item, null, false)
        return SceneDetailHolder(holder)
    }

    fun updateProcessBar(url: String,FILE_NAME: String, processBar: RoundProgressBar?){
        val folder = context.getExternalFilesDir(title)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        var localPath = folder.getPath() + File.separator + FILE_NAME
        DownloadHelper.download(url, localPath, object : DownloadHelper.OnDownloadListener {
            override fun onFail(file: File, failInfo: String?) {
            }

            override fun onProgress(progress: Int?) {
                processBar?.progress = progress!!
            }

            override fun onStart() {
                processBar?.progress =0
            }

            override fun onSuccess(file: File) {
                SharedPreferencesUtil.setGsonString(context,FILE_NAME,mPratice)
                notifyDataSetChanged()
            }
        })
    }

    fun setOnItemClickListener( listener : OnItemDetailClickListener){
        this.mClickListener = listener
    }

    fun changetShowDelImage(isShow: Boolean,position: Int,pratice : PraticeSections) {
        mPosition = position
        this.isShow = isShow
        mPratice = pratice
        notifyDataSetChanged()
    }

    inner class SceneDetailHolder(view : View?): RecyclerView.ViewHolder(view) {
        var mTotleItem : TextView?
        var mItemImage : ImageView?
        var mDownloadImage : ImageView?
        var mSceneVip : TextView?
        var mScenePlay : ImageView?
        var mProcessBar : RoundProgressBar?
        var mLinearLayout : LinearLayout?

        init {
            mTotleItem = view?.findViewById(R.id.recycle_scene_detail_tv)
            mItemImage = view?.findViewById(R.id.recycle_scene_detail_img)
            mDownloadImage = view?.findViewById(R.id.scene_download)
            mSceneVip = view?.findViewById(R.id.recycle_scene_vip)
            mProcessBar = view?.findViewById(R.id.scene_download_mp3)
            mLinearLayout = view?.findViewById(R.id.scene_item)
            mScenePlay = view?.findViewById(R.id.scene_paly)
            mProcessBar?.setStyle(RoundProgressBar.FILL)
            mProcessBar?.circleProgressColor = ResourcesUtils.resource.getColor(R.color.wordWhite)
            mProcessBar?.roundWidth = 1.toFloat()
            mProcessBar?.circleColor = ResourcesUtils.resource.getColor(R.color.wordWhite)
            mProcessBar?.setTextIsDisplayable(false)

            mLinearLayout?.setOnClickListener(this@SceneDetailAdapter)
            mDownloadImage?.setOnClickListener(this@SceneDetailAdapter)
        }

    }
    enum class ViewName {
        DOWNLOAD,
        SCENEITEM
    }

    interface OnItemDetailClickListener {
        fun onItemClick(v: View, viewName: ViewName, position: Int)
    }

    override fun onClick(v: View) {
        val position = v.getTag()      //getTag()获取数据
        if (mClickListener != null) {
            when (v.getId()) {
                R.id.scene_download -> mClickListener?.onItemClick(v, ViewName.DOWNLOAD, position as Int)
                else -> mClickListener?.onItemClick(v, ViewName.SCENEITEM, position as Int)
            }
        }
    }

}



