package com.mubarak.newscastmb.ui.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mubarak.newscastmb.data.sources.remote.dto.SourceItem
import com.mubarak.newscastmb.databinding.NewsSourceItemLayoutBinding
import javax.inject.Inject

/**Paging Adapter for NewsSourcesFragment*/
class NewsSourcesPagingAdapter @Inject constructor(): PagingDataAdapter<SourceItem,
        NewsSourcesPagingAdapter.NewsSourcesViewHolder>(COMPARATOR) {

    class NewsSourcesViewHolder(binding: NewsSourceItemLayoutBinding): RecyclerView.ViewHolder(binding.root){

        val tvSourceName = binding.tvDiscoverNewsSourceName
        val tvSourceDescription = binding.tvDiscoverNewsSourceDescription
        val tvCategory = binding.tvDiscoverSourceCategory
        val root = binding.root

    }
    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<SourceItem>(){
            override fun areItemsTheSame(oldItem: SourceItem, newItem: SourceItem)=
                oldItem == newItem

            override fun areContentsTheSame(oldItem: SourceItem, newItem: SourceItem)=
                oldItem.url == newItem.url

        }
    }

    override fun onBindViewHolder(holder: NewsSourcesViewHolder, position: Int) {
        val list = getItem(position)
        holder.apply {

            tvSourceName.text  =list?.name
            tvSourceDescription.text = list?.description
            tvCategory.text = list?.category
            root.setOnClickListener {
                list?.let {
                    onItemClickListener(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSourcesViewHolder {
        return NewsSourcesViewHolder(
            NewsSourceItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    private lateinit var onItemClickListener: ((SourceItem) -> Unit)

    fun onSourceNewsItemClick(listener: ((SourceItem) -> Unit)){
        onItemClickListener = listener
    }
}