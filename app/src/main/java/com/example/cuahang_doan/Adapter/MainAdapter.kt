package com.example.cuahang_doan.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.ArrayList

class MainAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    private val arrayFragment = ArrayList<Fragment>()
    private val arrayTitle = ArrayList<String>()
    override fun getItem(position: Int): Fragment {
        return arrayFragment[position]
    }

    override fun getCount(): Int {
        return arrayFragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return arrayTitle[position]
    }

    fun addFragment(fm: Fragment, title: String) {
        arrayFragment.add(fm)
        arrayTitle.add(title)
    }
}