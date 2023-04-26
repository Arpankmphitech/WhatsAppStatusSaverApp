package com.example.whatsappstatussaverapp.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.whatsappstatussaverapp.Utils
import com.example.whatsappstatussaverapp.adapter.DwImageAdapter
import com.example.whatsappstatussaverapp.databinding.FragmentDwImageBinding
import java.io.File

class DwImageFragment : Fragment() {

    lateinit var binding: FragmentDwImageBinding
    private val DIRECTORY_TO_SAVE_MEDIA_NOW = "/WSDownloader/"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDwImageBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        binding.apply {

            Utils.gridLayoutManager(activity, rcvDwImage)

            val adapterDwImage = DwImageAdapter(
                getListFiles(
                    File(
                        Environment.getExternalStorageDirectory()
                            .toString() + DIRECTORY_TO_SAVE_MEDIA_NOW
                    )
                ), activity
            ) { imageUri ->

                showImage(imageUri)
            }

            rcvDwImage.adapter = adapterDwImage

            adapterDwImage.notifyDataSetChanged()
        }

    }

    private fun showImage(imageUri: Uri) {

        val builder = context?.let { Dialog(it) }
        builder!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        builder!!.getWindow()!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        builder.setOnDismissListener(DialogInterface.OnDismissListener {
            //nothing;
        })

        val imageView = ImageView(context)
        imageView.setImageURI(imageUri)
        builder.addContentView(
            imageView, RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        builder.show()

    }

    private fun getListFiles(parentDir: File): ArrayList<File>? {
        val inFiles: ArrayList<File> = ArrayList<File>()
        if (parentDir.exists()) {
            val files: Array<File>
            files = parentDir.listFiles()
            for (file in files) {
                if (file.getName().endsWith(".jpg")) {
                    if (!inFiles.contains(file)) inFiles.add(file)
                }
            }

        }


        return inFiles
    }


}