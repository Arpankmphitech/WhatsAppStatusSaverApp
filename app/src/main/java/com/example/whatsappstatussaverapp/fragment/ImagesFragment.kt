package com.example.whatsappstatussaverapp.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.whatsappstatussaverapp.Utils
import com.example.whatsappstatussaverapp.adapter.ImageAdapter
import com.example.whatsappstatussaverapp.databinding.FragmentImagesBinding
import java.io.File


class ImagesFragment : Fragment() {

    lateinit var binding: FragmentImagesBinding
    private val WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        binding.apply {

            Utils.gridLayoutManager(activity, rcvImage)

            val adapterImage = ImageAdapter(
                getListFiles(
                    File(
                        Environment.getExternalStorageDirectory()
                            .toString() + WHATSAPP_STATUSES_LOCATION
                    )
                ), activity
            ) { imageUri ->

                showImage(imageUri)
            }
            adapterImage.notifyDataSetChanged()
            rcvImage.adapter = adapterImage

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
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        builder.show()

    }

    private fun getListFiles(parentDir: File): ArrayList<File> {
        val inFiles: ArrayList<File> = ArrayList<File>()
        val files: Array<File>
        files = parentDir.listFiles()!!
        for (file in files) {
            if (file.getName().endsWith(".jpg")
            ) {
                if (!inFiles.contains(file)) inFiles.add(file)
            }
        }
        return inFiles
    }

}