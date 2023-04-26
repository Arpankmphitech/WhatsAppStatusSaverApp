package com.example.whatsappstatussaverapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.whatsappstatussaverapp.Utils
import com.example.whatsappstatussaverapp.activity.VideoPlayerActivity
import com.example.whatsappstatussaverapp.adapter.DwVideoAdapter
import com.example.whatsappstatussaverapp.databinding.FragmentDwVideoBinding
import java.io.File

class DwVideoFragment : Fragment() {

    lateinit var binding: FragmentDwVideoBinding
    private val DIRECTORY_TO_SAVE_MEDIA_NOW = "/WSDownloader"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDwVideoBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        binding.apply {

            Utils.gridLayoutManager(activity, rcvDwVideo)


            val adapterDwVideo = DwVideoAdapter(
                getListFiles(
                    File(
                        Environment.getExternalStorageDirectory()
                            .toString() + DIRECTORY_TO_SAVE_MEDIA_NOW
                    )
                ), activity
            ) { uri ->
                val intent = Intent(context, VideoPlayerActivity::class.java)
                intent.putExtra("uri", uri)
                startActivity(intent)
            }

            rcvDwVideo.setAdapter(adapterDwVideo)

            adapterDwVideo.notifyDataSetChanged()
        }

    }

    private fun getListFiles(parentDir: File): ArrayList<File>? {
        val inFiles: ArrayList<File> = ArrayList<File>()
        val files: Array<File>
        files = parentDir.listFiles()
        for (file in files) {
            if (file.getName().endsWith(".mp4")) {
                if (!inFiles.contains(file)) inFiles.add(file)
            }
        }
        return inFiles
    }


}