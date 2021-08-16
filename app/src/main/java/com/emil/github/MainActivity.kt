package com.emil.github

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.emil.github.databinding.ActivityMainBinding
import com.emil.github.ext.getVmFactory
import com.emil.github.util.PageInfo
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainActivityViewModel> { getVmFactory() }

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.navigation_users -> {
                if (viewModel.currentPage != PageInfo.USERS) {
                    viewModel.setCurrentPage(PageInfo.USERS)
                    return@OnNavigationItemSelectedListener true
                }
                Log.d("tttttttt", "PageInfo.USERS")
            }
            R.id.navigation_mine -> {
                if (viewModel.currentPage != PageInfo.MY_INFO) {
                    viewModel.setCurrentPage(PageInfo.MY_INFO)
                    return@OnNavigationItemSelectedListener true
                }
                Log.d("tttttttt", "PageInfo.MY_INFO")
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setupNavController()
        setupBottomNav()
        setPageChangeObserver()

    }

    private fun setPageChangeObserver() {
        viewModel.isPageChange.observe(this, Observer {
            if (it) {
                when(viewModel.currentPage.value) {
                    PageInfo.USERS -> binding.bottomNavView.selectedItemId = R.id.navigation_users
                    PageInfo.MY_INFO -> binding.bottomNavView.selectedItemId = R.id.navigation_mine
                }
                viewModel.pageChangeDone()
            }
        })
    }

    private fun setupBottomNav() {
        binding.bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun setupNavController() {
        navController = findNavController(R.id.myNavHostFragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(controller.currentDestination?.id) {
                R.id.detailFragment -> {
                    supportActionBar?.hide()
                    binding.bottomNavView.visibility = View.INVISIBLE
                }
                else -> {
                    supportActionBar?.show()
                    binding.bottomNavView.visibility = View.VISIBLE
                }
            }
        }
    }
}