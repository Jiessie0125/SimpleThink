package com.example.simple.simplethink.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.example.simple.simplethink.utils.ResourcesUtils.Companion.resource

/**
 * Created by jiessie on 2019/7/4.
 */
object ImageUtil {
    fun createWaterMaskLeftTop(context: Context, src: Bitmap, watermark: Bitmap,
                               paddingLeft: Float, paddingTop: Float): Bitmap? {
        return createWaterMaskBitmap(src, watermark,
                dp2px(context, paddingLeft), dp2px(context, paddingTop))
    }

    private fun createWaterMaskBitmap(src: Bitmap?, watermark: Bitmap,
                                      paddingLeft: Float, paddingTop: Float): Bitmap? {
        if (src == null) {
            return null
        }
        val width = src!!.getWidth()
        val height = src!!.getHeight()
        //创建一个bitmap
        val newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        val canvas = Canvas(newb).apply {
            //在画布 0，0坐标上开始绘制原始图片
            drawBitmap(src, 0f, 0f, null)
            //在画布上绘制水印图片
            drawBitmap(watermark, paddingLeft, paddingTop, null)
            // 保存
            save()
            // 存储
            restore()
        }
        return newb
    }

    fun dp2px(context: Context, dp: Float): Float {
        val scale = context.getResources().getDisplayMetrics().density
        return dp * scale + 0.5f
    }

    fun showBKImage(imageUrl: String, activity: Activity, imageView: View){

        val simpleTarget = object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                imageView.setBackground(resource)
            }
        }
        Glide.with(activity).load(imageUrl).into(simpleTarget)
    }

}