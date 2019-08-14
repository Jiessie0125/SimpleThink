package com.example.simple.simplethink.totle.fragment.whiteNoisePage

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simple.simplethink.MyApp
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.WhiteNoiseItemResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.activity.RecyclerViewSpacesItemDecoration
import com.example.simple.simplethink.totle.adapter.OnWhiteItemClickListener
import com.example.simple.simplethink.totle.adapter.WhiteItemAdapter
import com.example.simple.simplethink.utils.DownloadHelper
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_scene_paly.*
import kotlinx.android.synthetic.main.fragment_white_noise.*
import java.io.File
import java.io.IOException

/**
 * Created by jiessie on 2019/6/5.
 */
class WhithNoiseFragment : Fragment(),WhiteNoiseContact.View ,View.OnClickListener{
    
    lateinit var persenter: WhiteNoiseContact.Presenter
    lateinit var whiteItemAdapter: WhiteItemAdapter
    var player : MediaPlayer?= null
    var isStop = false

    val handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            white_item_play_time.setText(FilesUtils.timeParse(msg.what.toLong()))
        }
    }
    companion object {
        const val WHITEITEM = "WHITEITEM"
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_white_noise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        white_play.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init(){
        player = MediaPlayer()
        val httpResposityImpl = HttpResposityImpl()
        persenter = WhiteNoisePresenter(httpResposityImpl, this,this.activity!!)
        persenter.getWhiteNoise()
    }

    override fun updateView(list: List<WhiteNoiseItemResponse>) {
        whiteItemAdapter = WhiteItemAdapter(this.activity!!,list)
        white_noise_rv.layoutManager  = GridLayoutManager(this.activity, 3) as RecyclerView.LayoutManager?
        val stringIntegerHashMap = HashMap< String, Int>()
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 25)//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, 25)
        white_noise_rv.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
        white_noise_rv.adapter = whiteItemAdapter
        whiteItemAdapter.notifyDataSetChanged()
        whiteItemAdapter.setOnItemClickListener(object : OnWhiteItemClickListener{
            override fun onItemClick(v: View?, position: Int) {
               // releasePlay()
                whiteItemAdapter.changetShowDelImage(true,position)
                white_play.setImageResource(R.drawable.white_stop)
                white_item_play_time.text = ResourcesUtils.getString(R.string.white_noise_time)
                updateProcessBar(list[position].url,list[position].title)
            }
        })
    }

    fun play(sceneSource : String){
        player?.reset()
        try {
            player?.setDataSource(sceneSource)
            player?.prepare()
            player?.start()
            Thread( MyThread()).start()
        } catch ( e: IOException) {
            e.printStackTrace()
        }
    }

    fun updateProcessBar(url: String,FILE_NAME: String){
        val filePath = Environment.getExternalStorageDirectory().toString() + File.separator + WHITEITEM
        val folder = File(filePath)
        if (!folder?.exists()) {
            folder?.mkdirs()
        }
        val localPath = folder?.getPath() + File.separator + FILE_NAME
        val localFolder = File(localPath)
        if(localFolder.exists()){
            play(localPath)
        }else{
            DownloadHelper.download(url, localPath, object : DownloadHelper.OnDownloadListener {
                override fun onFail(file: File, failInfo: String?) {
                }

                override fun onProgress(progress: Int?) {

                }

                override fun onStart() {
                }

                override fun onSuccess(file: File) {
                    play(localPath)
                }
            })
        }
    }


    internal inner class MyThread : Runnable {

        override fun run() {
            while (player != null && isStop == false) {
                // 将SeekBar位置设置到当前播放位置
                handler.sendEmptyMessage(player!!.getCurrentPosition())
                try {
                    // 每100毫秒更新一次位置
                    Thread.sleep(80)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.white_play -> {
                if(player?.isPlaying!!){
                    player?.pause()
                    white_play.setImageResource(R.drawable.white_play)
                }else{
                    player?.start()
                    white_play.setImageResource(R.drawable.white_stop)
                }

            }
        }
    }

    fun createFragment(): WhithNoiseFragment {
        val bundle = Bundle()
        val fragment = WhithNoiseFragment()
        fragment.setArguments(bundle)

        return fragment
    }

    override fun onDestroy() {
        releasePlay()
        super.onDestroy()
    }

    private fun releasePlay(){
        isStop = false
        player?.let {
            if(player!!.isPlaying()){
                player!!.stop();
            }
            player!!.release();
        }
    }

    override fun onPause() {
        player?.let {
            if (player!!.isPlaying()) {
                player!!.pause()
            }
        }
        super.onPause()
    }

    override fun onResume() {
        player?.let {
            if (!player!!.isPlaying()) {
                player!!.start()
            }
        }
        super.onResume()
    }
}