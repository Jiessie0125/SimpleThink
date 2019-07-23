package com.example.simple.simplethink.totle.activity


import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.example.simple.simplethink.R
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.FilesUtils.timeParse
import com.example.simple.simplethink.utils.ImageUtil.showBKImage
import kotlinx.android.synthetic.main.activity_scene_paly.*
import java.io.IOException






/**
 * Created by jiessie on 2019/7/13.
 */
class ScenePlayActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val SCENERESOURCE = "SCENERESOURCE"
        const val SCENENAME = "SCENENAME"
        const val BKGROUND = "BKGROUND"
        fun newIntent (context: Context?,sceneName : String, sceneSource: String,bkground: String) : Intent {
            var intent = Intent(context, ScenePlayActivity::class.java)
            intent.putExtra(SCENENAME,sceneName)
            intent.putExtra(SCENERESOURCE,sceneSource)
            intent.putExtra(BKGROUND,bkground)
            return intent
        }
    }

    val handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            mSeekbar?.setProgress(msg.what)
            scene_item_play.setText(timeParse(msg.what.toLong()))
        }
    }

    var player : MediaPlayer ?= null
    var mSeekbar : SeekBar ?= null
    var isStop = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_paly)
        initView()
        progress_bar_healthy.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    player!!.seekTo(i)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                player?.seekTo(mSeekbar?.progress!!)
            }
        })
    }
    private fun initView(){
        val intent = intent
        player = MediaPlayer()
        mSeekbar = progress_bar_healthy
        scene_paly_close.setOnClickListener(this)
        scene_play.setOnClickListener(this)
        val sceneName = intent.getSerializableExtra(SCENENAME) as String
        val sceneSource = intent.getSerializableExtra(SCENERESOURCE) as String
        val bkground = intent.getSerializableExtra(BKGROUND) as String
        showBKImage(bkground,this,scene_play_bg)
        sceneName?.let { scene_item_name.text = it }
        play(sceneSource)
        sceneSource?.let {
            scene_item_totle.text = FilesUtils.timeParse(player?.getDuration()?.toLong())
        }
    }

    fun play(sceneSource : String){
        player?.reset()
        try {
            player?.setDataSource(sceneSource)
            player?.prepare()
            player?.start()
            Thread( SeekBarThread()).start()
        } catch ( e: IOException) {
            e.printStackTrace()
        }
        mSeekbar?.setMax(player?.duration!!)
    }

    internal inner class SeekBarThread : Runnable {

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
    override fun onDestroy() {
        isStop = true
        player?.let {
            if(player!!.isPlaying()){
                player!!.stop();
            }
            player!!.release();
        }
        super.onDestroy()
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.scene_paly_close -> {
                this.finish()
            }
            R.id.scene_play -> {
                if(player?.isPlaying!!){
                    player?.pause()
                    scene_play.setImageResource(R.drawable.scene_play)
                }else{
                    player?.start()
                    scene_play.setImageResource(R.drawable.scene_stop)
                }

            }
        }
    }

    override fun finish(){
        handler.removeCallbacksAndMessages(null)
        isStop = true
        super.finish()
    }

}