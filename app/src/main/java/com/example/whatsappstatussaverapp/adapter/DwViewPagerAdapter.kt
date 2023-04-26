package com.example.whatsappstatussaverapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class DwViewPagerAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(
    supportFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val mFragment = ArrayList<Fragment>()
    private val mTitleList = ArrayList<String>()


    override fun getCount(): Int {
        return mFragment.size

    }

    override fun getItem(position: Int): Fragment {
        return mFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragment.add(fragment)
        mTitleList.add(title)
    }

}