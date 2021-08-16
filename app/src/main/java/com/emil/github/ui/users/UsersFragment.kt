package com.emil.github.ui.users

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.emil.github.MainActivityViewModel
import com.emil.github.databinding.FragmentUsersBinding
import com.emil.github.databinding.ItemUserDataBinding
import com.emil.github.ext.getVmFactory
import com.emil.github.ui.MainFragmentDirections

class UsersFragment : Fragment() {
    lateinit var binding: FragmentUsersBinding
    lateinit var adapter: UsersItemAdapter
    private val viewModel by viewModels<UsersViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupRecyclerView()
        setupObserver()


        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = UsersItemAdapter(UsersItemAdapter.OnClickListener {
            viewModel.getUserDetail(it.url)
//            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment())
        })
        binding.recyclerViewUsers.adapter = adapter
        binding.recyclerViewUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState) // check at list bottom
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.i("addOnScrollListener", "end list")
                    viewModel.getMoreUsers()
                }
            }
        })
    }

    private fun setupObserver() {
        viewModel.userList.observe(viewLifecycleOwner, Observer { userList ->
            userList?.let {
                Log.d("ttttttttt", "$it")
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.userDetail.observe(viewLifecycleOwner, Observer { userDetail ->
            userDetail?.let {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(it))
                viewModel.navToDetailDone()
            }
        })
    }

}