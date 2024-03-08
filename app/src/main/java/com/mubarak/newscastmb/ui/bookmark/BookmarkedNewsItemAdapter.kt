package com.mubarak.newscastmb.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.mubarak.newscastmb.R
import com.mubarak.newscastmb.data.sources.local.BookmarkNews
import com.mubarak.newscastmb.data.sources.local.NewsItems
import com.mubarak.newscastmb.databinding.NewsItemLayoutBinding

class BookmarkedNewsItemAdapter(
) : ListAdapter<BookmarkNews, BookmarkedNewsItemAdapter.HomeViewHolder>(diffCallBack) {

    inner class HomeViewHolder(private val binding: NewsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookmarkNews: BookmarkNews) {
            binding.apply {
                tvNewsTitle.text = bookmarkNews.title
                tvAuthor.text = bookmarkNews.author
                newsImage.load(bookmarkNews.imageUrl) {
                    transformations(RoundedCornersTransformation(radius = 30f))
                    placeholder(R.drawable.news_loading_state)
                  //  error(R.drawable.breaking_news)

                }
                tvPublishedAt.text = bookmarkNews.publishedAt
            }
        }
    }

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<BookmarkNews>() {
            override fun areItemsTheSame(oldItem: BookmarkNews, newItem: BookmarkNews) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: BookmarkNews, newItem: BookmarkNews) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            NewsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}


