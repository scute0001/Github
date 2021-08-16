package com.emil.github.ui.myinfo

import android.annotation.SuppressLint
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.emil.github.R
import com.emil.github.databinding.FragmentMyInfoBinding
import com.emil.github.ext.getVmFactory
import com.emil.github.util.GithubLoginManager
import java.util.concurrent.TimeUnit

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

        binding.btnGithubLogin.setOnClickListener {
            setupGithubWebViewDialog()
        }

        viewModel.myInfo.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.user = it
                binding.clMyInfo.visibility = View.VISIBLE
                binding.clGithubLogin.visibility = View.GONE
            } else {
                binding.clMyInfo.visibility = View.GONE
                binding.clGithubLogin.visibility = View.VISIBLE
            }
        })

        return binding.root
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