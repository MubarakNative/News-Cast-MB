package com.mubarak.newscastmb.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mubarak.newscastmb.R
import com.mubarak.newscastmb.data.sources.local.BookmarkNews
import com.mubarak.newscastmb.databinding.FragmentBookmarkedNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkedNewsFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkedNewsBinding
    private lateinit var draggedNews: BookmarkNews
    private val viewModel: BookMarkNewsViewModel by viewModels()
    private lateinit var adapter: BookmarkedNewsItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkedNewsBinding.inflate(
            layoutInflater, container,
            false
        )
        adapter = BookmarkedNewsItemAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookmarkedNewsList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.isBookmarkedArticleEmpty.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                binding.bookmarkPlaceholder.visibility = View.VISIBLE
            } else binding.bookmarkPlaceholder.visibility = View.GONE
        }

        binding.bookmarkToolbar.setOnMenuItemClickListener {

            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.opt_search_bookmark -> {
                    findNavController().navigate(R.id.action_bookmarkedNewsFragment_to_searchNewsFragment)
                    true
                }

                else -> false
            }

        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                draggedNews = adapter.currentList[viewHolder.bindingAdapterPosition]
                viewModel.deleteBookmarkedNote(draggedNews)
            }

        }).attachToRecyclerView(binding.bookmarkedNewsList)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.getAllNews().collect {
                    adapter.submitList(it)
                    binding.bookmarkedNewsList.adapter = adapter
                }
            }
        }
    }

}