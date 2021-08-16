package com.emil.github.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.ViewBindingAdapter.setClickListener
import com.emil.github.GithubApplication
import com.emil.github.R
import com.emil.github.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        initViewInfo()
        setClick()
        return binding.root
    }

    private fun setClick() {
        binding.btnClose.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initViewInfo() {
        binding.data = DetailFragmentArgs.fromBundle(requireArguments()).user
    }

}