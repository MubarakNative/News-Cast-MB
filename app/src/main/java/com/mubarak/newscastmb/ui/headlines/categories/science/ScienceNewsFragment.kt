package com.mubarak.newscastmb.ui.headlines.categories.science

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mubarak.newscastmb.databinding.FragmentScienceNewsBinding
import com.mubarak.newscastmb.ui.adapters.TrendingNewsPagingAdapter
import com.mubarak.newscastmb.ui.headlines.HeadlineNewsFragmentDirections
import com.mubarak.newscastmb.ui.viewmodel.MainViewModel
import com.mubarak.newscastmb.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScienceNewsFragment : Fragment() {
    private lateinit var binding: FragmentScienceNewsBinding
    private lateinit var pagingAdapter: TrendingNewsPagingAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScienceNewsBinding.inflate(
            layoutInflater,
            container,
            false
        )


        pagingAdapter = TrendingNewsPagingAdapter()

        setUpRecyclerView(binding.rvHeadlineScienceList)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.categoryNews(AppConstants.SCIENCE_NEWS_CATEGORY).collect{
                    pagingAdapter.submitData(lifecycle,it)
                }
            }
        }


        pagingAdapter.onNewsItemClick { newsItem->

            val action = HeadlineNewsFragmentDirections.actionHeadlineNewsFragmentToDetailedNewsFragment(newsItem)
            Navigation.findNavController(requireView()).navigate(action)
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