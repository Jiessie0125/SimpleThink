package com.example.simple.simplethink.vip

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.support.annotation.Nullable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

/**
 * Created by jiessie on 2019/8/5.
 */

class CircleImageView @JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var width: Float = 0.toFloat()
    private var height: Float = 0.toFloat()
    private var radius: Float = 0.toFloat()
    private val paint: Paint
    private val matrixs: Matrix

    init {
        paint = Paint()
        paint.setAntiAlias(true)   //设置抗锯齿
        matrixs = Matrix()      //初始化缩放矩阵
    }

    /**
     * 测量控件的宽高，并获取其内切圆的半径
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width = getMeasuredWidth().toFloat()
        height = getMeasuredHeight().toFloat()
        radius = Math.min(width, height) / 2
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = getDrawable()
        if (drawable == null) {
            super.onDraw(canvas)
            return
        }
        if (drawable is BitmapDrawable) {
            paint.setShader(initBitmapShader(drawable as BitmapDrawable))//将着色器设置给画笔
            canvas.drawARGB(0, 0, 0, 0)//画布设置透明
            canvas.drawCircle(width / 2, height / 2, radius, paint)//使用画笔在画布上画圆
            return
        }
        super.onDraw(canvas)
    }

    /**
     * 获取ImageView中资源图片的Bitmap，利用Bitmap初始化图片着色器,通过缩放矩阵将原资源图片缩放到铺满整个绘制区域，避免边界填充
     */
    private fun initBitmapShader(drawable: BitmapDrawable): BitmapShader {
        val bitmap = drawable.getBitmap()
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val scale = Math.max(width / bitmap.getWidth(), height / bitmap.getHeight())
        matrixs.setScale(scale, scale)//将图片宽高等比例缩放，避免拉伸
        bitmapShader.setLocalMatrix(matrixs)
        return bitmapShader
    }
}
