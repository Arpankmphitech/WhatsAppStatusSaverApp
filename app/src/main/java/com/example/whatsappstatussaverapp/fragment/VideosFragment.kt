package com.example.whatsappstatussaverapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whatsappstatussaverapp.Utils
import com.example.whatsappstatussaverapp.activity.VideoPlayerActivity
import com.example.whatsappstatussaverapp.adapter.VideoAdapter
import com.example.whatsappstatussaverapp.databinding.FragmentVideosBinding
import java.io.File

class VideosFragment : Fragment() {

    lateinit var binding: FragmentVideosBinding
    private val WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVideosBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        binding.apply {

            Utils.gridLayoutManager(activity, rcvVideo)

            val adapterVideo = VideoAdapter(
                getListFiles(
                    File(
                        Environment.getExternalStorageDirectory()
                            .toString() + WHATSAPP_STATUSES_LOCATION
                    )
                ), activity
            ) { uri ->
                val intent = Intent(context, VideoPlayerActivity::class.java)
                intent.putExtra("uri", uri)
                startActivity(intent)
            }

            adapterVideo.notifyDataSetChanged()

            rcvVideo.setAdapter(adapterVideo)

        }

    }

    private fun getListFiles(Dir: File): ArrayList<File> {
        val inFiles: ArrayList<File> = ArrayList<File>()
        val files: Array<File>
        files = Dir.listFiles()!!
        for (file in files) {
            if (file.getName().endsWith(".mp4")
            ) {
                if (!inFiles.contains(file)) inFiles.add(file)
            }
        }
        return inFiles
    }

}