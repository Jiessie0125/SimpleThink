package com.example.simple.simplethink.totle.fragment.whiteNoisePage

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.WhiteNoiseItem
import com.example.simple.simplethink.totle.BaseFragment
import com.example.simple.simplethink.totle.activity.RecyclerViewSpacesItemDecoration
import com.example.simple.simplethink.totle.adapter.OnWhiteItemClickListener
import com.example.simple.simplethink.totle.adapter.WhiteItemAdapter
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import kotlinx.android.synthetic.main.fragment_white_noise.*
import java.io.IOException
import java.util.*


/**
 * Created by jiessie on 2019/6/5.
 */
class WhithNoiseFragment : Fragment(), View.OnClickListener {

    lateinit var persenter: WhiteNoiseContact.Presenter
    lateinit var whiteItemAdapter: WhiteItemAdapter
    private var PLAYER_TIME: Int = 10 * 60 * 1000
    var player: MediaPlayer? = null
    var isStop = false
    private val mHasLoadedOnce = false
    private var mposition = 0
    private var isPrepared = false
    private var timer:Timer?=null

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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(!isVisibleToUser){
            releasePlay()
        }else{
            player = MediaPlayer()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {

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
            if(set_time_layout.visibility == View.GONE){
                set_time_layout.visibility = View.VISIBLE
            }else{
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
        player = MediaPlayer()
        whiteItemAdapter = WhiteItemAdapter(this.activity!!, list)
        white_noise_rv.layoutManager = GridLayoutManager(this.activity, 3) as RecyclerView.LayoutManager?
        val stringIntegerHashMap = HashMap<String, Int>()
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 25)//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, 25)
        white_noise_rv.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
        white_noise_rv.adapter = whiteItemAdapter
        whiteItemAdapter.notifyDataSetChanged()
        isPrepared = true
        whiteItemAdapter.setOnItemClickListener(object : OnWhiteItemClickListener {
            override fun onItemClick(v: View?, position: Int) {
               // releasePlay()
                if(player == null)player = MediaPlayer()
                isStop = false
                mposition = position
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
            var img_selected = 0
            var url = ""
            when (index) {
                1 -> {
                    title = getString(R.string.white_noise_1)
                    img = R.drawable.yu
                    img_selected =  R.drawable.yu_selected
                    url = "m01.mp3";
                }
                2 -> {
                    title = getString(R.string.white_noise_2)
                    img = R.drawable.ocean
                    img_selected =  R.drawable.ocean_selected
                    url = "m02.mp3";
                }
                3 -> {
                    title = getString(R.string.white_noise_3)
                    img = R.drawable.bird
                    img_selected =  R.drawable.bird_selected
                    url = "m03.mp3";
                }
                4 -> {
                    title = getString(R.string.white_noise_4)
                    img = R.drawable.shuidi
                    img_selected =  R.drawable.shuidi_selected
                    url = "m04.mp3";
                }
                5 -> {
                    title = getString(R.string.white_noise_5)
                    img = R.drawable.fire
                    img_selected =  R.drawable.fire_selected
                    url = "m05.mp3";
                }
                6 -> {
                    title = getString(R.string.white_noise_6)
                    img = R.drawable.wing
                    img_selected =  R.drawable.wing_selected
                    url = "m06.mp3";
                }
                7 -> {
                    title = getString(R.string.white_noise_7)
                    img = R.drawable.train
                    img_selected =  R.drawable.train_selected
                    url = "m07.mp3";
                }
                8 -> {
                    title = getString(R.string.white_noise_8)
                    img = R.drawable.shuye
                    img_selected =  R.drawable.shuye_selected
                    url = "m08.mp3";
                }
                9 -> {
                    title = getString(R.string.white_noise_9)
                    img = R.drawable.shui
                    img_selected =  R.drawable.shui_selected
                    url = "m09.mp3";
                }

            }
            val whiteNoiseItem = WhiteNoiseItem(index, title, img, img_selected, url, index.toString())
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
            startTimer()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun startTimer(){
        timer?.cancel()
        timer = Timer()
        timer?.schedule(object :TimerTask(){
            override fun run() {
                if (player != null && isStop == false) {
                    if (player!!.getCurrentPosition() > PLAYER_TIME) {
                        player!!.stop()
                    }
                    if(player!!.isPlaying) {
                        // 将SeekBar位置设置到当前播放位置
                        handler.sendEmptyMessage(player!!.getCurrentPosition())
                    }
                }

            }
        },0,500)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.white_play -> {
                player?.let {
                    if (player?.isPlaying!!) {
                        player?.pause()
                        white_play.setImageResource(R.drawable.white_play)
                    } else {
                        if(mposition == 0){
                            play("m01.mp3")
                            isStop = false
                        }
                        else{
                            player?.start()
                        }
                        white_play.setImageResource(R.drawable.white_stop)
                    }
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
                player!!.stop()
            }
            player!!.release()
            player = null
            mposition = 0
        }
        if(isPrepared){
            white_play.setImageResource(R.drawable.white_play)
            white_item_play_time.text = ResourcesUtils.getString(R.string.white_noise_time)
            whiteItemAdapter.changetShowDelImage(true, 0)
        }
        timer?.cancel()
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
       /* player?.let {
            if (!player!!.isPlaying()) {
                player!!.start()
            }
        }*/
        super.onResume()
    }



    override fun onDestroyView() {
        releasePlay()
        isPrepared = false
        super.onDestroyView()

    }

}