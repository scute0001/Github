package com.emil.github.ui.myinfo

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.emil.github.databinding.FragmentMyInfoBinding
import com.emil.github.ext.getVmFactory
import com.emil.github.network.LoadApiStatus
import com.emil.github.util.GithubLoginManager

class MyInfoFragment : Fragment() {
    val viewModel by viewModels<MyInfoViewModel> { getVmFactory() }

    lateinit var binding: FragmentMyInfoBinding
    lateinit var githubDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyInfoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupClickListener()
        setupObserver()

        return binding.root
    }

    private fun setupClickListener() {
        binding.btnGithubLogin.setOnClickListener {
            setupGithubWebViewDialog()
        }
    }

    private fun setupObserver() {
        viewModel.myInfo.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.user = it
                binding.svMyInfo.visibility = View.VISIBLE
                binding.clGithubLogin.visibility = View.GONE
            } else {
                binding.svMyInfo.visibility = View.GONE
                binding.clGithubLogin.visibility = View.VISIBLE
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    LoadApiStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    LoadApiStatus.DONE -> binding.progressBar.visibility = View.GONE
                    LoadApiStatus.ERROR -> binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupGithubWebViewDialog() {
        githubDialog = Dialog(requireContext())
        val webView = WebView(requireContext())
        webView.apply {
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            webViewClient = GithubWebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(GithubLoginManager.getCodeUrl())
        }
        githubDialog.setContentView(webView)
        githubDialog.show()

    }

    inner class GithubWebViewClient: WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            viewModel.getGithubCode(request?.url.toString())

            if (request!!.url.toString().contains("code=")) {
                githubDialog.dismiss()
                return true
            }
            return false
        }
    }

}