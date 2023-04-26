package com.example.whatsappstatussaverapp.adapter

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsappstatussaverapp.R
import com.example.whatsappstatussaverapp.databinding.ImageItemBinding
import de.mateware.snacky.Snacky
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel


class ImageAdapter(
    var list: ArrayList<File>?, var activity: FragmentActivity?, val onItemClicked: (Uri) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private val DIRECTORY_TO_SAVE_MEDIA_NOW = "/WSDownloader/"


    class ViewHolder(var binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {

            val currentFile: File = list!!.get(position)

            imgDownloadIcon.setOnClickListener {
                imgDownloadIconFun(currentFile, holder)
            }

            imgShareIcon.setOnClickListener {
                imgShareIcon(currentFile)
            }

            if (currentFile.absolutePath.endsWith(".jpg")) {
                downloadedData(
                    File(
                        (Environment.getExternalStorageDirectory()
                            .toString() + DIRECTORY_TO_SAVE_MEDIA_NOW) + currentFile.name
                    ), holder
                )

                val myBitmap = BitmapFactory.decodeFile(currentFile.absolutePath)
                Log.e("TAG", "onBindViewHolder==$myBitmap")
                imgWhatsappImage.setImageBitmap(myBitmap)
            }

            imageLayout.setOnClickListener {
                var imageUri = Uri.fromFile(File(currentFile.absolutePath))
                onItemClicked.invoke(imageUri)
            }
        }

    }

    private fun downloadedData(destFile: File, holder: ViewHolder) {
        if (destFile.exists()) {

            holder.binding.imgDownloadIcon.setVisibility(View.INVISIBLE)
        }
    }

    private fun imgShareIcon(currentFile: File) {

        val targetIntent = Intent(Intent.ACTION_SEND)
        targetIntent.type = "image/*"
        targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        targetIntent.putExtra(Intent.EXTRA_SUBJECT, activity!!.getString(R.string.app_name))
        targetIntent.putExtra(Intent.EXTRA_TEXT, "")
        val fileURI = FileProvider.getUriForFile(
            activity!!, activity!!.packageName + ".provider", File(currentFile.absolutePath)
        )
        targetIntent.putExtra(Intent.EXTRA_STREAM, fileURI)
        activity!!.startActivity(targetIntent)


    }

    private fun imgDownloadIconFun(currentFile: File, holder: ViewHolder) {

        try {
            copyFile(
                currentFile, File(
                    (Environment.getExternalStorageDirectory()
                        .toString() + DIRECTORY_TO_SAVE_MEDIA_NOW).toString() + currentFile.name
                ), holder
            )
            Snacky.builder().setActivty(activity).setText("Image Is Successfully Downloaded")
                .success().show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("RecyclerV", "onClick: Error:" + e.message)
            Snacky.builder().setActivty(activity).setText("save_error").error().show()
        }

    }

    @Throws(IOException::class)
    fun copyFile(sourceFile: File?, destFile: File, holder: ViewHolder) {
        if (!destFile.parentFile.exists()) destFile.parentFile.mkdirs()
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        var source: FileChannel? = null
        var destination: FileChannel? = null
        try {
            source = FileInputStream(sourceFile).getChannel()
            destination = FileOutputStream(destFile).getChannel()
            destination.transferFrom(source, 0, source.size())
        } finally {
            if (source != null) {
                downloadedData(destFile, holder)
                source.close()
            } else {
                holder.binding.imgDownloadIcon.setVisibility(View.VISIBLE)
            }
            if (destination != null) {
                destination.close()
            }
        }
    }


    override fun getItemCount(): Int {
        return list!!.size
    }
}