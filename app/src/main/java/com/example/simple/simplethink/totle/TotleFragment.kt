package com.example.simple.simplethink.totle


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Gravity
import android.util.TypedValue
import android.widget.TextView



/**
 * Created by jiessie on 2019/6/5.
 */
class TotleFragment : Fragment() {
    val ARGUMENT = "ARGUMENT"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val bundle = arguments
        var text: String? = ""
        if (bundle != null) {
            text = bundle.getString(ARGUMENT)
        }
        val tv = TextView(activity)
        tv.text = text
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
        tv.gravity = Gravity.CENTER

        return tv
    }

    fun createFragment(argument: String): TotleFragment {
        val bundle = Bundle()
        bundle.putString(ARGUMENT, argument)

        val fragment = TotleFragment()
        fragment.setArguments(bundle)

        return fragment
    }
}