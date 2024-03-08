package com.mubarak.newscastmb.ui.trending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mubarak.newscastmb.R
import com.mubarak.newscastmb.data.sources.local.NewsItems
import com.mubarak.newscastmb.databinding.FragmentTrendingNewsBinding
import com.mubarak.newscastmb.ui.adapters.TrendingNewsPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingNewsFragment : Fragment() {

    private lateinit var binding: FragmentTrendingNewsBinding
    private lateinit var pagerAdapter: TrendingNewsPagingAdapter
    private val viewModel: TrendingNewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrendingNewsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        pagerAdapter = TrendingNewsPagingAdapter()
        setUpRecyclerView(binding.trendingNewsList)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeToolBar.setNavigationOnClickListener {
            navigateToSearch()
        }

        binding.homeToolBar.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.opt_settings_trending -> {
                    navigateToSettings()
                    true
                }

                else -> false
            }
        }

        pagerAdapter.onNewsItemClick {
            navigateToDetailNewsFragment(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.getAllNews.collect {
                    pagerAdapter.submitData(lifecycle, it)
                }
            }
        }
    }

    private fun navigateToSettings() {
        findNavController().navigate(
            R.id.action_trendingNewsFragment_to_settingsHostFragment
        )
    }

    private fun navigateToDetailNewsFragment(content: NewsItems) {
        val action =
            TrendingNewsFragmentDirections.actionTrendingNewsFragmentToDetailedNewsFragment(
                content
            )
        findNavController().navigate(action)
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pagerAdapter
            setHasFixedSize(true)
        }

    }

    private fun navigateToSearch(){
        findNavController().navigate(R.id.action_trendingNewsFragment_to_searchNewsFragment)
    }

}