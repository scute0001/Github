package com.emil.github.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.emil.github.ui.myinfo.MyInfoFragment
import com.emil.github.ui.users.UsersFragment
import com.emil.github.util.PageInfo

const val TOTAL_PAGES = 2

class MainFragmentPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return TOTAL_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            PageInfo.USERS.pageCode -> UsersFragment()
            PageInfo.MY_INFO.pageCode -> MyInfoFragment()
            else -> UsersFragment()
        }
    }


}