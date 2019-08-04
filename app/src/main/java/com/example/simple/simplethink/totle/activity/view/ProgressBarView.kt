package com.example.simple.simplethink.totle.activity.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ProgressBar
import com.example.simple.simplethink.R
import com.example.simple.simplethink.utils.ResourcesUtils

/**
 * Created by jiessie on 2019/7/31.
 */

class ProgressBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ProgressBar(context, attrs, defStyleAttr) {
    //字体大小
    protected var mTextSize = 12
    //字体颜色
    protected var mTextColor = ResourcesUtils.resource.getColor(R.color.blackSuggesstion)
    //没有到达(右边progressbar的颜色)
    protected var mUnReachColor = ResourcesUtils.resource.getColor(R.color.mainColor)
    //progressbar的高度
    protected var mProgressHeight = 55
    //progressbar进度的颜色
    protected var mReachColor = ResourcesUtils.resource.getColor(R.color.courseDetailDownload)
    //字体间距
    protected var mTextOffset = 0

    protected var mPaint: Paint
    //progressbar真正的宽度
    protected var mRealWith: Int = 0
    protected var mWidth : Int =0

    init {
        //获取自定义属性
        obtainStyledAttrs(attrs)

        mPaint = Paint()
        mPaint.setTextSize(mTextSize.toFloat())
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private fun obtainStyledAttrs(attrs: AttributeSet?) {
        val ta = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressBarView)
        mTextSize = ta.getDimensionPixelSize(R.styleable.ProgressBarView_progress_text_size, sp2px(mTextSize.toFloat()))
        mTextColor = ta.getColor(R.styleable.ProgressBarView_progress_text_color, mTextColor)

        mUnReachColor = ta.getColor(R.styleable.ProgressBarView_progress_unreach_color, mUnReachColor)
        mProgressHeight = ta.getDimension(R.styleable.ProgressBarView_progress_height, dp2px(mProgressHeight.toFloat()).toFloat()).toInt()

        mReachColor = ta.getColor(R.styleable.ProgressBarView_progress_reach_color, mReachColor)

        mTextOffset = ta.getDimension(R.styleable.ProgressBarView_progress_text_offset, dp2px(mTextOffset.toFloat()).toFloat()).toInt()
        ta.recycle()
    }

    @Synchronized override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthVal = MeasureSpec.getSize(widthMeasureSpec)
        mWidth = widthVal
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(widthVal, height)
        //计算progressbar真正宽度=控件的宽度-paddingleft-paddingright
        mRealWith = getMeasuredWidth() - getPaddingLeft()
    }

    @Synchronized override fun onDraw(canvas: Canvas) {
        //保存画布
        canvas.save()
        //移动画布
        canvas.translate(getPaddingLeft().toFloat(), getHeight().toFloat() / 2)
        //定义变量用来控制是否要绘制右边progressbar  如果宽度不够的时候就不进行绘制
       // var noNeedUnRech = false
        //计算左边进度在整个控件宽度的占比
        val radio = getProgress().toFloat() / getMax()
        //获取左边进度的宽度
        var progressX = radio * mRealWith
        //中间文字
        val text = getProgress().toString() + "%"
        //获取文字的宽度
        val textWidth = mPaint.measureText(text)
       /* if (progressX + textWidth > mRealWith) {
            //左边进度+文字的宽度超过progressbar的宽度 重新计算左边进度的宽度 这个时候也就意味着不需要绘制右边进度
            progressX = (mRealWith - textWidth).toFloat()
         //   noNeedUnRech = true
        }*/
        //计算左边进度结束的位置 如果结束的位置小于0就不需要绘制左边的进度
        val endX = progressX
       // if (endX > 0) {
            //绘制左边进度
            mPaint.setColor(mReachColor)
            mPaint.setStrokeWidth(mProgressHeight.toFloat())
            canvas.drawLine(0f, 0f, endX, 0f, mPaint)
       // }
        mPaint.setColor(mTextColor)
        if (getProgress() != 0) {
            //计算文字基线
            val y = (-(mPaint.descent() + mPaint.ascent()) / 2)
            //绘制文字
            if(progress == 100){
                mPaint.setColor(mReachColor)
                canvas.drawText("", (mWidth-max-25).toFloat(), y, mPaint)
            }else{
                canvas.drawText(text, (mWidth-max-25).toFloat(), y, mPaint)
            }
        }

        //重置画布
        canvas.restore()

    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        var result = 0
        //获取高度模式
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        //获取宽度模式
        val size = MeasureSpec.getSize(heightMeasureSpec)
        if (mode == MeasureSpec.EXACTLY) {
            //精准模式 用户设置为 比如80dp  match_parent fill_parent
            result = size
        } else {
            //计算中间文字的高度
            val textHeight = (mPaint.descent() - mPaint.ascent())
            //paddingTop+paddingBottom+ progressbar高度和文字高度的最大值
            result = getPaddingTop() + getPaddingBottom() + Math.max(mProgressHeight, Math.abs(textHeight.toInt()))
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size)
            }
        }
        return result
    }

    protected fun dp2px(dip: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics()).toInt()
    }

    protected fun sp2px(sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics()).toInt()
    }
}
