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
import com.example.whatsappstatussaverapp.databinding.DwImageItemBinding
import de.mateware.snacky.Snacky
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel


class DwImageAdapter(
    var list: ArrayList<File>?, var activity: FragmentActivity?, val onItemClicked: (Uri) -> Unit
) : RecyclerView.Adapter<DwImageAdapter.ViewHolder>() {


    class ViewHolder(val binding: DwImageItemBinding?) : RecyclerView.ViewHolder(binding?.root!!)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding =
            DwImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding?.apply {
            val currentFile: File = list!!.get(position)

            imgShareIcon.setOnClickListener {
                imgShareIcon(currentFile)
            }

            imgDeletImageIcon.setOnClickListener {
                currentFile.delete()
            }

            if (currentFile.absolutePath.endsWith(".jpg")) {
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

    override fun getItemCount(): Int {
        return list!!.size
    }
}