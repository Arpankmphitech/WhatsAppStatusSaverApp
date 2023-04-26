package com.example.whatsappstatussaverapp

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

object Utils {

    fun gridLayoutManager(activity: FragmentActivity?, rcvVideo: RecyclerView) {

        val mLinearLayoutManager = GridLayoutManager(activity, 2)
        rcvVideo.setLayoutManager(mLinearLayoutManager)

    }
}