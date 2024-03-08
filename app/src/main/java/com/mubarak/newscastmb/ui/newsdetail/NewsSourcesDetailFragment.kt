package com.mubarak.newscastmb.ui.newsdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mubarak.newscastmb.R
import com.mubarak.newscastmb.databinding.FragmentNewsSourcesDetailBinding


class NewsSourcesDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsSourcesDetailBinding
    private val navArgs: NewsSourcesDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsSourcesDetailBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = navArgs.sourceItem.url

        binding.discoverDetailedNewsToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.discoverDetailedNewsToolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.shareSourceNews -> {
                    requireContext().shareArticle(url)
                    true

                }

                else -> false
            }
        }

        binding.discoverDetailedWebView.apply {

            val web = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.discoverPgWebLoading.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.discoverPgWebLoading.visibility = View.GONE
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view?.loadUrl(request?.url.toString())
                    return true
                }


            }
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            settings.javaScriptEnabled = true
            webViewClient = web
            loadUrl(url)

        }

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.btnView)

        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.newsSourcesDetailedFragment) {

                navBar.visibility = View.GONE
            } else {

                navBar.visibility = View.VISIBLE
            }
        }

    }

    override fun onDestroy() {
        binding.discoverDetailedWebView.stopLoading()
        binding.discoverDetailedWebView.clearCache(true)
        super.onDestroy()
    }

    private fun Context.shareArticle(url: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        val sendIntent = Intent.createChooser(
            shareIntent, "Share this Article to"
        )
        startActivity(sendIntent)
    }

}