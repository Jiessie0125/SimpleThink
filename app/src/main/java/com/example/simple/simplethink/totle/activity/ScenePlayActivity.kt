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
import kotlinx.android.synthetic.main.activity_scene_paly.*
import java.io.IOException


/**
 * Created by jiessie on 2019/7/13.
 */
class ScenePlayActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val SCENERESOURCE = "SCENERESOURCE"
        const val SCENENAME = "SCENENAME"
        fun newIntent (context: Context?,sceneName : String, sceneSource: String) : Intent {
            var intent = Intent(context, ScenePlayActivity::class.java)
            intent.putExtra(SCENENAME,sceneName)
            intent.putExtra(SCENERESOURCE,sceneSource)
            return intent
        }
    }

    val handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            progress_bar_healthy.setProgress(msg.what)
            scene_item_play.setText(timeParse(msg.what.toLong()))
            Log.e("","--test--"+msg.what)
        }
    }

    var player : MediaPlayer ?= null

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

            }
        })
    }
    private fun initView(){
        val intent = intent
        player = MediaPlayer()
        val sceneName = intent.getSerializableExtra(SCENENAME) as String
        val sceneSource = intent.getSerializableExtra(SCENERESOURCE) as String
        sceneName?.let { scene_item_name.text = it }
//        scene_paly_close.setOnClickListener { finish() }
        play(sceneSource)
        sceneSource?.let {
            scene_item_totle.text = FilesUtils.timeParse(player?.getDuration()?.toLong())
        }
    }

//    fun getSource(sceneSource : String) : String{
//        val mediaPlayer =  MediaPlayer()
//        mediaPlayer.setDataSource(sceneSource)
//        mediaPlayer.prepare()
//        return FilesUtils.timeParse(mediaPlayer.getDuration().toLong())
//    }
//
//    fun getLongMusic(sceneSource: String): Int{
//        val mediaPlayer =  MediaPlayer()
//        mediaPlayer.setDataSource(sceneSource)
//        mediaPlayer.prepare()
//        return mediaPlayer.getDuration()
//    }

    fun play(sceneSource : String){
        player?.reset()
        try {
            player?.setDataSource(sceneSource)
            player?.prepare()
            player?.start()
        } catch ( e: IOException) {
            e.printStackTrace();
        }
        Thread( SeekBarThread()).start()
        progress_bar_healthy.setMax(player?.duration!!)
    }

    internal inner class SeekBarThread : Runnable {

        override fun run() {
            while (player != null /*&& isStop == false*/) {
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
        super.onDestroy()
        player!!.reset()
        //isStop = true
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.scene_paly_close -> finish()
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
}