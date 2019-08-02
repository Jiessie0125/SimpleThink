package com.example.simple.simplethink.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.example.simple.simplethink.R

/**
 * Created by Ashur on 2019/8/2.
 */
class WaitDialog(context: Context) : Dialog(context) {
    private val waitText: TextView

    init {
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER)
        setContentView(R.layout.widget_wait_dialog)
        waitText = findViewById(R.id.tv_wait_dialog_text)
    }

    fun setText(waitMsg: CharSequence) {
        waitText.visibility = View.VISIBLE
        waitText.text = waitMsg
    }

    fun setText(resId: Int) {
        setText(context.getString(resId))
    }
}