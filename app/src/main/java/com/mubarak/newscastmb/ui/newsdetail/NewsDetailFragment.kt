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
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.mubarak.newscastmb.R
import com.mubarak.newscastmb.data.sources.local.BookmarkNews
import com.mubarak.newscastmb.databinding.FragmentNewsDetailBinding
import com.mubarak.newscastmb.ui.bookmark.BookMarkNewsViewModel_HiltModules_KeyModule_ProvideFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding

    lateinit var webView: WebView

    private val navArgs: NewsDetailFragmentArgs by navArgs()
    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailBinding.inflate(
            layoutInflater,
            container,
            false
        )

        webView = binding.detailedWebView

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = navArgs.newsItem.url

        webView.apply {


            val web = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.pgWebLoading.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.pgWebLoading.visibility = View.GONE
                }
            }

            webViewClient = web
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            settings.javaScriptEnabled = true
            loadUrl(url)

        }

        /*  val callback = object : OnBackPressedCallback(true) {
              override fun handleOnBackPressed() {
                  if (webView.canGoBack()) {
                      webView.goBack()
                  } else {
                      isEnabled = false
                      requireActivity().onBackPressed()
                  }
              }
          }*/

        binding.bottomAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.shareArticle -> {
                    requireContext().shareArticle(url)
                    true

                }

                R.id.bookmarkArticle -> {
                    bookmarkArticle()
                    Snackbar.make(view, R.string.article_bookmarked, Snackbar.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.btnView)

        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailedNewsFragment) {

                navBar.visibility = View.GONE
            } else {
                navBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        binding.detailedWebView.stopLoading()
        binding.detailedWebView.clearCache(true)
        super.onDestroy()
    }

    private fun Context.shareArticle(url: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        val sendIntent = Intent.createChooser(
            shareIntent, "Share this Article to..."
        )
        startActivity(sendIntent)
    }

    private fun bookmarkArticle() {
        val bookmarkNews = BookmarkNews(
            title = navArgs.newsItem.title,
            url = navArgs.newsItem.url,
            author = navArgs.newsItem.author,
            imageUrl = navArgs.newsItem.urlToImage,
            publishedAt = navArgs.newsItem.publishedAt
        )
        viewModel.insertNews(bookmarkNews)
    }
}