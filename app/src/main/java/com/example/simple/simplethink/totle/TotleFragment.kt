package com.example.simple.simplethink.totle


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simple.simplethink.R


/**
 * Created by jiessie on 2019/6/5.
 */
class TotleFragment : Fragment() {
    val ARGUMENT = "ARGUMENT"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_totle,container,false)
    }

    fun createFragment(): TotleFragment {
        val bundle = Bundle()

        val fragment = TotleFragment()
        fragment.setArguments(bundle)

        return fragment
    }
}