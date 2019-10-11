package com.example.simple.simplethink.totle.activity.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

/**
 * Created by jiessie on 2019/7/15.
 */

class RoundProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {
    /**
     * 画笔对象的引用
     */
    private var paint: Paint? = null

    /**
     * 圆环的颜色
     */
    /**
     * 设置圆环的颜色
     */
    var circleColor: Int = 0

    /**
     * 圆环进度的颜色
     */
    /**
     * 设置圆环进度的颜色
     */
    var circleProgressColor: Int = 0

    /**
     * 中间进度百分比的字符串的颜色
     */
    /**
     * 设置中间进度百分比的字符串的颜色
     */
    var textColor: Int = 0

    /**
     * 中间进度百分比的字符串的字体
     */
    /**
     * 设置中间进度百分比的字符串的字体大小
     */
    var textSize: Float = 0.toFloat()

    /**
     * 圆环的宽度
     */
    /**
     * 设置圆环的宽度
     */
    var roundWidth: Float = 0.toFloat()

    /**
     * 最大进度
     */
    private var max: Int = 0

    /**
     * 当前进度
     */
    /**
     * 获取进度.需要同步
     *
     * @return
     */
    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    @get:Synchronized
    var progress: Int = 0
        @Synchronized set(progress) {
            var progress = progress
            if (progress < 0) {
                throw IllegalArgumentException("progress not less than 0")
            }
            if (progress > max) {
                progress = max
            }
            if (progress <= max) {
                field = progress
                postInvalidate()
            }

        }
    /**
     * 是否显示中间的进度
     */
    private var textIsDisplayable: Boolean = false

    /**
     * 进度的风格，实心或者空心
     */
    private var style: Int = 0

    init {
        init()
    }

    private fun init() {
        paint = Paint()
        circleColor = -0x245ec
        circleProgressColor = -0x374f3
        textColor = -0xff0100
        textSize = 15f
        roundWidth = 5f
        max = 100
        textIsDisplayable = true
        style = 0
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /**
         * 画最外层的大圆环
         */
        val centre = getWidth() / 2 // 获取圆心的x坐标
        val radius = (centre - roundWidth / 2).toInt() // 圆环的半径
        paint!!.setColor(circleColor) // 设置圆环的颜色
        if (style == FILL_UP) {
            paint!!.setStyle(Paint.Style.FILL_AND_STROKE)
        } else {
            paint!!.setStyle(Paint.Style.STROKE) // 设置空心
        }

        paint!!.setStrokeWidth(roundWidth) // 设置圆环的宽度
        paint!!.setAntiAlias(true) // 消除锯齿
        canvas.drawCircle(centre.toFloat(), centre.toFloat(), radius.toFloat(), paint) // 画出圆环


        /**
         * 画圆弧 ，画圆环的进度
         */

        // 设置进度是实心还是空心
        paint!!.setStrokeWidth(roundWidth) // 设置圆环的宽度
        paint!!.setColor(circleProgressColor) // 设置进度的颜色
        val oval = RectF((centre - radius).toFloat(), (centre - radius).toFloat(), (centre + radius).toFloat(), (centre + radius).toFloat()) // 用于定义的圆弧的形状和大小的界限

        when (style) {
            STROKE -> {
                paint!!.setStyle(Paint.Style.STROKE)
                canvas.drawArc(oval, -90.toFloat(), (360 * this.progress / max).toFloat(), false, paint) // 根据进度画圆弧
            }
            FILL -> {
                paint!!.setStyle(Paint.Style.FILL_AND_STROKE)
                if (this.progress != 0) {
                    canvas.drawArc(oval, -90.toFloat(), (360 * this.progress / max).toFloat(), true, paint) // 根据进度画圆弧
                }
            }
            FILL_UP -> {
                paint!!.setStyle(Paint.Style.FILL_AND_STROKE)
                if (this.progress != 0) {
                    var angle = 360 * this.progress / max
                    if (angle / 2 < 90) {
                        angle = Math.abs(90 - angle / 2)
                    } else {
                        angle = 360 - Math.abs(90 - angle / 2)
                    }
                    canvas.drawArc(oval, angle.toFloat(), (360 * this.progress / max).toFloat(), false, paint)
                }
            }
        }

        /**
         * 画进度百分比
         */
        paint!!.setStrokeWidth(0.toFloat())
        paint!!.setColor(textColor)
        paint!!.setTextSize(textSize)
        paint!!.setTypeface(Typeface.DEFAULT_BOLD) // 设置字体
        val percent = (this.progress.toFloat() / max.toFloat() * 100).toInt() // 中间的进度百分比，先转换成float在进行除法运算，不然都为0
        val textWidth = paint!!.measureText(percent.toString() + "%") // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间

        if (textIsDisplayable && percent != 0) {
            canvas.drawText(percent.toString() + "%", centre - textWidth / 2, centre + textSize / 2, paint) // 画出进度百分比
        }

    }

    @Synchronized
    fun getMax(): Int {
        return max
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    @Synchronized
    fun setMax(max: Int) {
        if (max < 0) {
            throw IllegalArgumentException("max not less than 0")
        }
        this.max = max
    }

    /**
     * 设置是否显示数字百分比
     *
     * @param flag
     */
    fun setTextIsDisplayable(flag: Boolean) {
        textIsDisplayable = flag
    }

    /**
     * 设置进度风格
     * 0空心，1实心，2实心从下到上
     * @param style
     */
    fun setStyle(style: Int) {
        this.style = style
    }

    companion object {

        val STROKE = 0
        val FILL = 1
        /**
         * 进度的风格，实心从下到上
         */
        val FILL_UP = 2
    }

}
