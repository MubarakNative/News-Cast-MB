package com.mubarak.newscastmb.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.mubarak.newscastmb.R
import com.mubarak.newscastmb.data.sources.local.NewsItems
import com.mubarak.newscastmb.databinding.NewsItemLayoutBinding
import javax.inject.Inject

class TrendingNewsPagingAdapter @Inject constructor() :
    PagingDataAdapter<NewsItems, TrendingNewsPagingAdapter.NewsViewHolder>(COMPARATOR) {

    class NewsViewHolder(binding: NewsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvNewsTitle
        val tvAuthor = binding.tvAuthor
        val tvPublishedTime = binding.tvPublishedAt
        val newsImage = binding.newsImage
        val rootItem = binding.root

    }


    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<NewsItems>() {
            override fun areItemsTheSame(oldItem: NewsItems, newItem: NewsItems) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: NewsItems, newItem: NewsItems) =
                oldItem.url == newItem.url

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        return NewsViewHolder(
            NewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val list = getItem(position)

        holder.apply {
            tvTitle.text = list?.title
            tvAuthor.text = list?.author
            tvPublishedTime.text = list?.publishedAt
            newsImage.load(list?.urlToImage) {
                transformations(RoundedCornersTransformation(radius = 30f))
                placeholder(R.drawable.news_loading_state)
                error(R.drawable.news_bg)

            }
            rootItem.setOnClickListener {
                list?.let{
                    onItemClickListener.invoke(it)
                }

            }
        }
    }

    private lateinit var onItemClickListener: ((NewsItems) -> Unit)

    fun onNewsItemClick(listener: ((NewsItems) -> Unit)){
        onItemClickListener = listener
    }

}
