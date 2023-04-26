package com.example.whatsappstatussaverapp.adapter

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.whatsappstatussaverapp.R
import com.example.whatsappstatussaverapp.databinding.VideoItemBinding
import de.mateware.snacky.Snacky
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel

class VideoAdapter(
    var list: ArrayList<File>?, var activity: FragmentActivity?, val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private val DIRECTORY_TO_SAVE_MEDIA_NOW = "/WSDownloader/"


    class ViewHolder(val binding: VideoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {

            val currentFile: File = list!!.get(position)

            imgDownloadVideoIcon.setOnClickListener {
                imgDownloadIcon(currentFile, holder)
            }

            imgVideoShareIcon.setOnClickListener {
                imgShareIcon(currentFile)
            }

            downloadedData(
                File(
                    (Environment.getExternalStorageDirectory()
                        .toString() + DIRECTORY_TO_SAVE_MEDIA_NOW) + currentFile.name
                ), holder
            )

            if (currentFile.absolutePath.endsWith(".mp4")) {

                var uri = Uri.fromFile(File(currentFile.absolutePath))

                activity?.let {
                    val requestOptions = RequestOptions()
                    Glide.with(it).load("Your URL").apply(requestOptions)
                        .thumbnail(Glide.with(activity!!).load(uri)).into(imgWhatsappImage)
                }
            }

            imgPlay.setOnClickListener {
                var uri = Uri.fromFile(File(currentFile.absolutePath)).toString()
                onItemClicked.invoke(uri)
            }
        }

    }

    private fun downloadedData(destFile: File, holder: ViewHolder) {
        if (destFile.exists()) {
            holder.binding.imgDownloadVideoIcon.setVisibility(View.INVISIBLE)
        }

    }

    private fun imgShareIcon(currentFile: File) {

        val targetIntent = Intent(Intent.ACTION_SEND)
        targetIntent.type = "video/*"
        targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        targetIntent.putExtra(Intent.EXTRA_SUBJECT, activity!!.getString(R.string.app_name))
        targetIntent.putExtra(Intent.EXTRA_TEXT, "")
        val fileURI = FileProvider.getUriForFile(
            activity!!, activity!!.packageName + ".provider", File(currentFile.absolutePath)
        )
        targetIntent.putExtra(Intent.EXTRA_STREAM, fileURI)
        activity!!.startActivity(targetIntent)


    }

    private fun imgDownloadIcon(currentFile: File, holder: ViewHolder) {
        try {
            copyFile(
                currentFile, File(
                    (Environment.getExternalStorageDirectory()
                        .toString() + DIRECTORY_TO_SAVE_MEDIA_NOW).toString() + currentFile.name
                ), holder
            )
            Snacky.builder().setActivty(activity).setText("Video Is Successfully Downloaded")
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
                holder.binding.imgDownloadVideoIcon.setVisibility(View.VISIBLE)
            }
            if (destination != null) {
//                Toast.makeText(activity, "======", Toast.LENGTH_SHORT).show()
                destination.close()
            }
        }
    }

}