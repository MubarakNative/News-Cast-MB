package com.mubarak.newscastmb.ui.headlines.categories.sports

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
import com.mubarak.newscastmb.data.sources.local.NewsItems
import com.mubarak.newscastmb.databinding.FragmentSportsNewsBinding
import com.mubarak.newscastmb.ui.adapters.TrendingNewsPagingAdapter
import com.mubarak.newscastmb.ui.headlines.HeadlineNewsFragmentDirections
import com.mubarak.newscastmb.ui.trending.TrendingNewsViewModel
import com.mubarak.newscastmb.ui.viewmodel.MainViewModel
import com.mubarak.newscastmb.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SportsNewsFragment : Fragment() {

    private lateinit var binding: FragmentSportsNewsBinding
    private lateinit var pagingAdapter: TrendingNewsPagingAdapter
    private val viewmodel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSportsNewsBinding.inflate(
            layoutInflater,
            container,
            false
        )


        pagingAdapter = TrendingNewsPagingAdapter()
        setUpRecyclerView(binding.rvHeadlineSportsList)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.categoryNews(AppConstants.SPORTS_NEWS_CATEGORY).collect {
                    pagingAdapter.submitData(lifecycle, it)
                }
            }
        }

        pagingAdapter.onNewsItemClick {
            navigateToDetailNewsFragment(it)
        }
    }

    private fun navigateToDetailNewsFragment(newsItems: NewsItems) {
        val action =
            HeadlineNewsFragmentDirections.actionHeadlineNewsFragmentToDetailedNewsFragment(
                newsItems
            )
        findNavController().navigate(action)
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = pagingAdapter
        }
    }

}