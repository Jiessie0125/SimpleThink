package com.example.simple.simplethink.totle.fragment.whiteNoisePage

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
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
import com.example.simple.simplethink.model.WhiteNoiseItem
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
import java.io.ObjectInputStream
import android.content.res.AssetFileDescriptor



/**
 * Created by jiessie on 2019/6/5.
 */
class WhithNoiseFragment : Fragment(), View.OnClickListener {

    lateinit var persenter: WhiteNoiseContact.Presenter
    lateinit var whiteItemAdapter: WhiteItemAdapter
    private var PLAYER_TIME: Int = 10 * 60 * 1000
    var player: MediaPlayer? = null
    var isStop = false

    val handler = object : Handler() {
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

    private fun init() {
        player = MediaPlayer()
        updateView()
        initNumber()
        cricle_number_5.isSelected = false
        cricle_number_10.isSelected = true
        cricle_number_15.isSelected = false
        cricle_number_20.isSelected = false
        cricle_number_30.isSelected = false
        cricle_number_5.setTextColor(resources.getColor(R.color.wordWhite))
        cricle_number_10.setTextColor(resources.getColor(R.color.logonButton))
        cricle_number_15.setTextColor(resources.getColor(R.color.wordWhite))
        cricle_number_20.setTextColor(resources.getColor(R.color.wordWhite))
        cricle_number_30.setTextColor(resources.getColor(R.color.wordWhite))
        white_set_time.setOnClickListener {
            if (set_time_layout.visibility == View.GONE) {
                set_time_layout.visibility = View.VISIBLE
            } else {
                set_time_layout.visibility = View.GONE
            }
        }
    }

    private fun initNumber() {

        cricle_number_5.isSelected = false
        cricle_number_10.isSelected = false
        cricle_number_15.isSelected = false
        cricle_number_20.isSelected = false
        cricle_number_30.isSelected = false

        cricle_number_5.setOnClickListener {
            cricle_number_5.isSelected = true
            cricle_number_10.isSelected = false
            cricle_number_15.isSelected = false
            cricle_number_20.isSelected = false
            cricle_number_30.isSelected = false
            PLAYER_TIME = 5 * 60 * 1000
            if (player != null && isStop == false && player!!.getCurrentPosition() > PLAYER_TIME) {
                player!!.stop()
            }
            cricle_number_5.setTextColor(resources.getColor(R.color.logonButton))
            cricle_number_10.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_15.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_20.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_30.setTextColor(resources.getColor(R.color.wordWhite))
        }

        cricle_number_10.setOnClickListener {
            cricle_number_5.isSelected = false
            cricle_number_10.isSelected = true
            cricle_number_15.isSelected = false
            cricle_number_20.isSelected = false
            cricle_number_30.isSelected = false
            PLAYER_TIME = 10 * 60 * 1000
            if (player != null && isStop == false && player!!.getCurrentPosition() > PLAYER_TIME) {
                player!!.stop()
            }

            cricle_number_5.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_10.setTextColor(resources.getColor(R.color.logonButton))
            cricle_number_15.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_20.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_30.setTextColor(resources.getColor(R.color.wordWhite))
        }

        cricle_number_15.setOnClickListener {
            cricle_number_5.isSelected = false
            cricle_number_10.isSelected = false
            cricle_number_15.isSelected = true
            cricle_number_20.isSelected = false
            cricle_number_30.isSelected = false
            PLAYER_TIME = 15 * 60 * 1000
            if (player != null && isStop == false && player!!.getCurrentPosition() > PLAYER_TIME) {
                player!!.stop()
            }

            cricle_number_5.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_10.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_15.setTextColor(resources.getColor(R.color.logonButton))
            cricle_number_20.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_30.setTextColor(resources.getColor(R.color.wordWhite))

        }

        cricle_number_20.setOnClickListener {
            cricle_number_5.isSelected = false
            cricle_number_10.isSelected = false
            cricle_number_15.isSelected = false
            cricle_number_20.isSelected = true
            cricle_number_30.isSelected = false
            PLAYER_TIME = 20 * 60 * 1000
            if (player != null && isStop == false && player!!.getCurrentPosition() > PLAYER_TIME) {
                player!!.stop()
            }

            cricle_number_5.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_10.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_15.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_20.setTextColor(resources.getColor(R.color.logonButton))
            cricle_number_30.setTextColor(resources.getColor(R.color.wordWhite))
        }

        cricle_number_30.setOnClickListener {
            cricle_number_5.isSelected = false
            cricle_number_10.isSelected = false
            cricle_number_15.isSelected = false
            cricle_number_20.isSelected = false
            cricle_number_30.isSelected = true
            PLAYER_TIME = 30 * 60 * 1000
            if (player != null && isStop == false && player!!.getCurrentPosition() > PLAYER_TIME) {
                player!!.stop()
            }

            cricle_number_5.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_10.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_15.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_20.setTextColor(resources.getColor(R.color.wordWhite))
            cricle_number_30.setTextColor(resources.getColor(R.color.logonButton))
        }
    }

    fun updateView() {
        val list = initWhiteNoiseItem()
        whiteItemAdapter = WhiteItemAdapter(this.activity!!, list)
        white_noise_rv.layoutManager = GridLayoutManager(this.activity, 3) as RecyclerView.LayoutManager?
        val stringIntegerHashMap = HashMap<String, Int>()
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 25)//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, 25)
        white_noise_rv.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
        white_noise_rv.adapter = whiteItemAdapter
        whiteItemAdapter.notifyDataSetChanged()
        whiteItemAdapter.setOnItemClickListener(object : OnWhiteItemClickListener {
            override fun onItemClick(v: View?, position: Int) {
                // releasePlay()
                whiteItemAdapter.changetShowDelImage(true, position)
                white_play.setImageResource(R.drawable.white_stop)
                white_item_play_time.text = ResourcesUtils.getString(R.string.white_noise_time)
                play(list[position].url)
            }
        })
    }

    fun initWhiteNoiseItem(): List<WhiteNoiseItem> {
        var list: List<WhiteNoiseItem> = ArrayList()
        for (index in 1 until 10) {
            var title = ""
            var img = 0
            var url = ""
            when (index) {
                1 -> {
                    title = getString(R.string.white_noise_1)
                    img = R.drawable.shuidi
                    url = "m01.mp3";
                }
                2 -> {
                    title = getString(R.string.white_noise_2)
                    img = R.drawable.shuidi
                    url = "m02.mp3";
                }
                3 -> {
                    title = getString(R.string.white_noise_3)
                    img = R.drawable.shuidi
                    url = "m03.mp3";
                }
                4 -> {
                    title = getString(R.string.white_noise_1)
                    img = R.drawable.shuidi
                    url = "m04.mp3";
                }
                5 -> {
                    title = getString(R.string.white_noise_5)
                    img = R.drawable.shuidi
                    url = "m05.mp3";
                }
                6 -> {
                    title = getString(R.string.white_noise_6)
                    img = R.drawable.shuidi
                    url = "m06.mp3";
                }
                7 -> {
                    title = getString(R.string.white_noise_7)
                    img = R.drawable.shuidi
                    url = "m07.mp3";
                }
                8 -> {
                    title = getString(R.string.white_noise_8)
                    img = R.drawable.shuidi
                    url = "m08.mp3";
                }
                9 -> {
                    title = getString(R.string.white_noise_9)
                    img = R.drawable.shuidi
                    url = "m09.mp3";
                }

            }
            val whiteNoiseItem = WhiteNoiseItem(index, title, img, url, index.toString())
            list+= whiteNoiseItem
        }
        return list
    }

    fun play(sceneSource: String) {
        player?.reset()
        try {
            val assetMg = context.getAssets()
            val fileDescriptor = assetMg.openFd(sceneSource)
            player?.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength())
            player?.prepare()
            player?.start()
            Thread(MyThread()).start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    internal inner class MyThread : Runnable {

        override fun run() {
            while (player != null && isStop == false) {
                if (player!!.getCurrentPosition() > PLAYER_TIME) {
                    player!!.stop()
                    continue
                }
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
        when (v?.id) {
            R.id.white_play -> {
                if (player?.isPlaying!!) {
                    player?.pause()
                    white_play.setImageResource(R.drawable.white_play)
                } else {
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

    private fun resetPlay() {
        isStop = true
        player?.let {
            if (player!!.isPlaying()) {
                player!!.stop();
            }
            player!!.reset();
        }
    }

    private fun releasePlay() {
        isStop = true
        player?.let {
            if (player!!.isPlaying()) {
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