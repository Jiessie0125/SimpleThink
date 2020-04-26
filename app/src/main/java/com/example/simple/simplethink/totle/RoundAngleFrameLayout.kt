package com.example.simple.simplethink.totle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.simple.simplethink.R

/**
 * Created by jiessie on 2019/7/28.
 */

class RoundAngleFrameLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    private var topLeftRadius: Float = 0.toFloat()
    private var topRightRadius: Float = 0.toFloat()
    private var bottomLeftRadius: Float = 0.toFloat()
    private var bottomRightRadius: Float = 0.toFloat()

    private val roundPaint: Paint
    private val imagePaint: Paint

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundAngleFrameLayout)
            val radius = ta.getDimension(R.styleable.RoundAngleFrameLayout_radius, 0f)
            topLeftRadius = ta.getDimension(R.styleable.RoundAngleFrameLayout_topLeftRadius, radius)
            topRightRadius = ta.getDimension(R.styleable.RoundAngleFrameLayout_topRightRadius, radius)
            bottomLeftRadius = ta.getDimension(R.styleable.RoundAngleFrameLayout_bottomLeftRadius, radius)
            bottomRightRadius = ta.getDimension(R.styleable.RoundAngleFrameLayout_bottomRightRadius, radius)
            ta.recycle()
        }
        roundPaint = Paint()
        roundPaint.setColor(Color.WHITE)
        roundPaint.setAntiAlias(true)
        roundPaint.setStyle(Paint.Style.FILL)
        roundPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT))

        imagePaint = Paint()
        imagePaint.setXfermode(null)
    }
    override fun dispatchDraw(canvas: Canvas) {
        canvas.saveLayer(RectF(0f, 0f, canvas.getWidth().toFloat(), canvas.getHeight().toFloat()), imagePaint, Canvas.ALL_SAVE_FLAG)
        super.dispatchDraw(canvas)
        canvas.drawARGB(0, 0, 0, 0)
        drawTopLeft(canvas)
        drawTopRight(canvas)
        drawBottomLeft(canvas)
        drawBottomRight(canvas)
        canvas.restore()
    }

    private fun drawTopLeft(canvas: Canvas) {
        if (topLeftRadius > 0) {
            val path = Path()
            path.moveTo(0f, topLeftRadius)
            path.lineTo(0f, 0f)
            path.lineTo(topLeftRadius, 0f)
            path.arcTo(RectF(0f, 0f, topLeftRadius * 2, topLeftRadius * 2),
                    -90f, -90f)
            path.close()
            canvas.drawPath(path, roundPaint)
        }
    }

    private fun drawTopRight(canvas: Canvas) {
        if (topRightRadius > 0) {
            val width = getWidth()
            val path = Path()
            path.moveTo(width - topRightRadius, 0f)
            path.lineTo(width.toFloat(), 0f)
            path.lineTo(width.toFloat(), topRightRadius)
            path.arcTo(RectF(width - 2 * topRightRadius, 0f, width.toFloat(),
                    topRightRadius * 2), 0f, -90f)
            path.close()
            canvas.drawPath(path, roundPaint)
        }
    }

    private fun drawBottomLeft(canvas: Canvas) {
        if (bottomLeftRadius > 0) {
            val height = getHeight()
            val path = Path()
            path.moveTo(0f, height - bottomLeftRadius)
            path.lineTo(0f, height.toFloat())
            path.lineTo(bottomLeftRadius, height.toFloat())
            path.arcTo(RectF(0f, height - 2 * bottomLeftRadius,
                    bottomLeftRadius * 2, height.toFloat()), 90f, 90f)
            path.close()
            canvas.drawPath(path, roundPaint)
        }
    }

    private fun drawBottomRight(canvas: Canvas) {
        if (bottomRightRadius > 0) {
            val height = getHeight()
            val width = getWidth()
            val path = Path()
            path.moveTo(width - bottomRightRadius, height.toFloat())
            path.lineTo(width.toFloat(), height.toFloat())
            path.lineTo(width.toFloat(), height - bottomRightRadius)
            path.arcTo(RectF(width - 2 * bottomRightRadius, height - 2 * bottomRightRadius, width.toFloat(), height.toFloat()), 0f, 90f)
            path.close()
            canvas.drawPath(path, roundPaint)
        }
    }

}