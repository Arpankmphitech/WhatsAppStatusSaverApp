package com.example.whatsappstatussaverapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsappstatussaverapp.R
import com.example.whatsappstatussaverapp.adapter.DwViewPagerAdapter
import com.example.whatsappstatussaverapp.databinding.FragmentDownloadedDataBinding

class DownloadedDataFragment : Fragment() {

    lateinit var binding: FragmentDownloadedDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadedDataBinding.inflate(inflater, container, false)

        binding.viwPager.setCurrentItem(binding.viwPager.getCurrentItem() - 1, true)

        setFragment()
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        setFragment()
    }

    private fun setFragment() {

        binding.apply {

            val adapterAllDw = DwViewPagerAdapter(childFragmentManager)

            adapterAllDw.addFragment(DwImageFragment(), "Image")
            adapterAllDw.addFragment(DwVideoFragment(), "Video")
            viwPager.adapter = adapterAllDw
            tab.setupWithViewPager(binding.viwPager)
            
            tab.getTabAt(0)!!.setIcon(R.drawable.images_icon)
            tab.getTabAt(1)!!.setIcon(R.drawable.video_icon)
        }

    }

}