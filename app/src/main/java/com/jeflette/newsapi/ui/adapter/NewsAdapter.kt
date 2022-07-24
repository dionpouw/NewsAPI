package com.jeflette.newsapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeflette.newsapi.R
import com.jeflette.newsapi.data.entity.News
import com.jeflette.newsapi.data.remote.response.Articles
import com.jeflette.newsapi.databinding.NewsItemBinding
import com.jeflette.newsapi.ui.llistnews.ListFragmentDirections
import com.jeflette.newsapi.util.withDateFormat

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val results: MutableList<Articles?>? = ArrayList()
    fun setList(result: List<Articles?>?) {
        results?.clear()
        if (result != null) {
            results?.addAll(result)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(news: Articles) {
            binding.newsTitle.text = news.title ?: ""
            binding.newsSource.text = news.source?.name ?: ""
            binding.newsDate.text = news.publishedAt?.withDateFormat() ?: ""
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .error(R.drawable.ic_launcher_background)
                .into(binding.newsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        results?.get(position)?.let { holder.binding(it) }
        holder.itemView.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToDetailFragment(
                    results?.get(position) ?: Articles()
                )
            findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int = results?.size ?: 0

    class NewsComparator : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
    }
}