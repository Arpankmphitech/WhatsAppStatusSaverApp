package com.example.whatsappstatussaverapp.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsappstatussaverapp.R
import com.example.whatsappstatussaverapp.adapter.ViewPagerAdapter
import com.example.whatsappstatussaverapp.databinding.ActivityMainBinding
import com.example.whatsappstatussaverapp.fragment.DownloadedDataFragment
import com.example.whatsappstatussaverapp.fragment.ImagesFragment
import com.example.whatsappstatussaverapp.fragment.VideosFragment


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viwPager.setCurrentItem(binding.viwPager.getCurrentItem() - 1, true)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setFragment()
    }

    private fun setFragment() {

        binding.apply {

            val adapter = ViewPagerAdapter(supportFragmentManager)

            adapter.addFragment(ImagesFragment(), "Image")
            adapter.addFragment(VideosFragment(), "Video")
            adapter.addFragment(DownloadedDataFragment(), "Downloaded")
            viwPager.adapter = adapter

            tab.setupWithViewPager(viwPager)
            adapter.notifyDataSetChanged()


            tab.getTabAt(0)!!.setIcon(R.drawable.images_icon)
            tab.getTabAt(1)!!.setIcon(R.drawable.video_icon)
            tab.getTabAt(2)!!.setIcon(R.drawable.folder)
        }
    }

}

