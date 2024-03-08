package com.mubarak.newscastmb.ui.headlines.categories.entertainment

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
import com.mubarak.newscastmb.databinding.FragmentEntertainmentNewsBinding
import com.mubarak.newscastmb.ui.trending.TrendingNewsPagingAdapter
import com.mubarak.newscastmb.ui.headlines.HeadlineNewsFragmentDirections
import com.mubarak.newscastmb.ui.viewmodel.MainViewModel
import com.mubarak.newscastmb.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EntertainmentNewsFragment : Fragment() {

    private lateinit var binding: FragmentEntertainmentNewsBinding
    private lateinit var pagingAdapter: TrendingNewsPagingAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentEntertainmentNewsBinding.inflate(
           layoutInflater,
            container,
            false
        )

        pagingAdapter = TrendingNewsPagingAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(binding.rvHeadlineEntertainment)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                viewModel.categoryNews(AppConstants.ENTERTAINMENT_NEWS_CATEGORY).collect {
                    pagingAdapter.submitData(lifecycle, it)
                }
            }
        }

        pagingAdapter.onNewsItemClick {
            val action = HeadlineNewsFragmentDirections.actionHeadlineNewsFragmentToDetailedNewsFragment(it)
            findNavController().navigate(action)
        }

    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = pagingAdapter
        }
    }

}