package com.example.simple.simplethink.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.*
import com.example.simple.simplethink.R
import java.io.File
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.media.Image
import android.view.*
import cn.sharesdk.framework.Platform
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.MyApp
import com.example.simple.simplethink.model.bean.ShareMediaBean
import com.example.simple.simplethink.utils.auth.AuthInstance
import kotlinx.android.synthetic.main.activity_update_userinfo.*


/**
 * Created by mobileteam on 2019/8/15.
 */
class SharePicturePopupWindow constructor(activity: Activity, courseName: String) : PopupWindow() {

    private val courseName: String = courseName
    val userInfo = AuthInstance.getInstance().userInfo
    private val activity:Activity = activity
    lateinit var picCloseBtn: ImageView
    lateinit var linearLayout: LinearLayout
    lateinit var frameLayout: FrameLayout
    lateinit var pic_course:TextView
    lateinit var continue_day_count:TextView
    lateinit var pic_duration_count:TextView
    lateinit var user_info_avatar:ImageView
    lateinit var update_nick_name_text:TextView
    lateinit var imageBkg: ImageView
    private lateinit var mPopView: View
    private val bean = ShareMediaBean()
    private val supportMediaList = arrayOf<String>(ShareMediaPopupWindow.WECHAT, ShareMediaPopupWindow.MOMENTS, ShareMediaPopupWindow.QQ, ShareMediaPopupWindow.QQSPACE, ShareMediaPopupWindow.WEIBO)


    init {
        init()
    }


    private fun init() {
        val inflater = LayoutInflater.from(activity)
        mPopView = inflater.inflate(R.layout.share_picture_pop, null)
        imageBkg = mPopView.findViewById<ImageView>(R.id.image_pkg)
        picCloseBtn = mPopView.findViewById<ImageView>(R.id.pic_close_img)
        linearLayout = mPopView.findViewById<LinearLayout>(R.id.share_img_btn)
        Glide.with(MyApp.context!!).load(R.drawable.share_course).into(imageBkg)
        frameLayout = mPopView.findViewById<FrameLayout>(R.id.pic_share_img)
        pic_course = mPopView.findViewById<TextView>(R.id.pic_course)
        pic_course.text = courseName
        continue_day_count = mPopView.findViewById<TextView>(R.id.pic_continue_day)
        continue_day_count.text = userInfo?.continueDay
        pic_duration_count = mPopView.findViewById<TextView>(R.id.pic_duration_count)
        pic_duration_count.text = userInfo?.durationCount
        user_info_avatar = mPopView.findViewById<ImageView>(R.id.user_info_avatar)
        Glide.with(MyApp.context!!).load(userInfo?.avatar).apply(RequestOptions().placeholder(R.drawable.photo)).into(user_info_avatar)
        update_nick_name_text = mPopView.findViewById<TextView>(R.id.pic_nick_name)
        update_nick_name_text.text = userInfo?.nickName


        picCloseBtn.setOnClickListener {
            dismiss()
        }
        linearLayout.setOnClickListener {
            initImage(frameLayout)
        }
        setPopupWindow()
    }

    private fun initImage(v:View){
        var bitmap = createBitmap(v)
        bean.shareType = Platform.SHARE_IMAGE
        bean.imageData = bitmap
        showPopFormBottom()
    }

    private fun showPopFormBottom() {
        val shareMediaPopupWindow = ShareMediaPopupWindow(activity, supportMediaList, bean)
        shareMediaPopupWindow.showAtLocation(activity.findViewById<View>(R.id.media_popup), Gravity.BOTTOM, 0, 0)
    }


    fun createBitmap(v:View):Bitmap {
        //测量使得view指定大小
         val bmp = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
         val c = Canvas(bmp)
         c.drawColor(Color.WHITE)
         v.draw(c)
         return bmp
}

    private fun setPopupWindow() {
        this.contentView = mPopView// 设置View
        this.width = RelativeLayout.LayoutParams.MATCH_PARENT// 设置弹出窗口的宽
        this.height = RelativeLayout.LayoutParams.MATCH_PARENT// 设置弹出窗口的高
        this.isFocusable = true// 设置弹出窗口可
        this.animationStyle = R.style.anim_fade// 设置动画
        this.setBackgroundDrawable(ColorDrawable(Color.GRAY))
        backgroundAlpha(0.5f)
        this.setOnDismissListener(object : PopupWindow.OnDismissListener{
            override fun onDismiss() {
                backgroundAlpha(1f)
            }
        })
        mPopView.setOnTouchListener(object : View.OnTouchListener {
            public override fun onTouch(v: View, event: MotionEvent): Boolean {
                val height = mPopView.getTop()
                val y = event.getY().toInt()
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss()
                    }
                }
                return true
            }
        })
    }

    private fun backgroundAlpha(bgAlpha:Float) {
        val lp = activity.window.attributes as WindowManager.LayoutParams
        lp.alpha = bgAlpha
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        activity.getWindow().setAttributes(lp)
    }


}