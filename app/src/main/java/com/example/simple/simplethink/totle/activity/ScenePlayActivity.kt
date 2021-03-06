package com.example.simple.simplethink.totle.activity


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.main.activity.PraticeContact
import com.example.simple.simplethink.model.*
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.FilesUtils.timeParse
import com.example.simple.simplethink.utils.GenerateUUID
import com.example.simple.simplethink.utils.ImageUtil.showBKImage
import com.example.simple.simplethink.utils.PackageModeUtils
import kotlinx.android.synthetic.main.activity_scene_paly.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * Created by jiessie on 2019/7/13.
 */
class ScenePlayActivity : BaseActivity(), View.OnClickListener, ScenePlayContact.View {
    override fun onLoadUserInfoSuccess() {
        var intent = Intent()
        intent.putExtra("courseName",sceneName)
        setResult(RESULT_CODE, intent)
        finish()
    }

    override fun onuploadPracticeSuccess() {
        persenter.loadUserInfo()
    }

    override fun onFailure(e: Throwable) {
        if(e.message == "HTTP 401 Unauthorized"){
            finish()
            return
        }
        ErrorHandler.showErrorWithToast(this, e)
    }

    companion object {
        const val RESULT_CODE = 2
        const val SCENERESOURCE = "SCENERESOURCE"
        const val SCENENAME = "SCENENAME"
        const val BKGROUND = "BKGROUND"
        const val SECTIONS = "SECTIONS"
        fun newIntent (context: Context?,sceneName : String, sceneSource: String,bkground: String?,sections: PraticeSections?) : Intent {
            var intent = Intent(context, ScenePlayActivity::class.java)
            intent.putExtra(SCENENAME,sceneName)
            intent.putExtra(SCENERESOURCE,sceneSource)
            intent.putExtra(BKGROUND,bkground)
            intent.putExtra(SECTIONS,sections)
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
    val persenter = ScenePlayPresenter()
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var sceneName : String? = null
    var courseList = ArrayList<CourseLogs>()
    var courseMap = HashMap<String,ArrayList<CourseLogs>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_paly)
        initView()
        persenter.bind(this)
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
        sceneName = intent.getSerializableExtra(SCENENAME) as String?
        val sceneSource = intent.getSerializableExtra(SCENERESOURCE) as String?
        val bkground = intent.getSerializableExtra(BKGROUND) as String?
        val sections =  intent.getSerializableExtra(SECTIONS) as PraticeSections?
        bkground?.let {
            viewScreen.visibility = View.VISIBLE
            showBKImage(bkground,this,scene_play_bg)
        }
        sceneName?.let { scene_item_name.text = it }
        sceneSource?.let {  play(sceneSource) }
        sceneSource?.let {
            scene_item_totle.text = FilesUtils.timeParse(player?.getDuration()?.toLong())
        }
        (player as MediaPlayer).setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer?) {
                val date = Date(System.currentTimeMillis())
                val unique_id = GenerateUUID.generateOneUUID()
                val course_id = sections?.course_id
                val section_id = sections?.id
                val audio_id = sections?.audio_id
                val completed_time = sdf.format(date)

                var course = "[ {" +
                        "\"unique_id\": \"$unique_id\"," +
                        "\"course_id\": $course_id, " +
                        "\"section_id\": $section_id," +
                        "\"audio_id\": $audio_id," +
                        "\"completed_time\": \"$completed_time\"" +
                        "} ]"
                persenter.uploadPractice(course)
            }
        })
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
        persenter.unbind()
        super.onDestroy()
    }

    override fun onPause() {
//        player?.let {
//            if (player!!.isPlaying()) {
//                player!!.pause()
//            }
//        }
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
                showDeleteDialog()
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

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(this)
                .setTitle("是否要结束本次训练？")
                .setPositiveButton("否", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .setNegativeButton("是", DialogInterface.OnClickListener { dialog, which ->
                    this.finish()
                })
        builder.create().show()

    }

}