package com.example.whatsappstatussaverapp.adapter

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.whatsappstatussaverapp.R
import com.example.whatsappstatussaverapp.databinding.DwVideoItemBinding
import java.io.File

class DwVideoAdapter(
    var list: ArrayList<File>?, var activity: FragmentActivity?, val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<DwVideoAdapter.ViewHolder>() {


    class ViewHolder(var binding: DwVideoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DwVideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {

            val currentFile: File = list!!.get(position)

            imgShareIcon.setOnClickListener {
                imgShareIcon(currentFile)
            }

            if (currentFile.absolutePath.endsWith(".mp4")) {

                var uri = Uri.fromFile(File(currentFile.absolutePath))

                val requestOptions = RequestOptions()
                Glide.with(activity!!).load("Your URL").apply(requestOptions)
                    .thumbnail(Glide.with(activity!!).load(uri)).into(imgWhatsappImage)

            }

            imgPlay.setOnClickListener {
                var uri = Uri.fromFile(File(currentFile.absolutePath)).toString()
                onItemClicked.invoke(uri)
            }

            imgDeletVideoIcon.setOnClickListener {
                currentFile.delete()
            }

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

}