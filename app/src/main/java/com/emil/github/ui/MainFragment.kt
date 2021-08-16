package com.emil.github.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.emil.github.MainActivityViewModel
import com.emil.github.databinding.FragmentMainBinding
import com.emil.github.util.Logger
import com.emil.github.util.PageInfo

class MainFragment : Fragment() {
    lateinit var viewModel: MainActivityViewModel
    lateinit var binding: FragmentMainBinding
    lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewPager = binding.mainViewPager
        viewPager.adapter = MainFragmentPagerAdapter(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        viewModel.currentPage.observe(viewLifecycleOwner, Observer {
            Logger.i("observe page=$it pagecode=${it.pageCode}")
            it?.let {
                when(it) {
                    PageInfo.USERS -> viewPager.setCurrentItem(PageInfo.USERS.pageCode, true)
                    PageInfo.MY_INFO -> viewPager.setCurrentItem(PageInfo.MY_INFO.pageCode, true)
                }
            }
        })
        setViewPagerListener()
    }

    private fun setViewPagerListener() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position) {
                    PageInfo.USERS.pageCode -> {
                        viewModel.setCurrentPage(PageInfo.USERS)
                        viewModel.setPageChange()
                    }
                    PageInfo.MY_INFO.pageCode -> {
                        viewModel.setCurrentPage(PageInfo.MY_INFO)
                        viewModel.setPageChange()
                    }
                }
            }
        })
    }
}