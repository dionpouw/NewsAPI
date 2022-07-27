package com.jeflette.newsapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeflette.newsapi.R
import com.jeflette.newsapi.data.entity.Articles
import com.jeflette.newsapi.databinding.NewsItemBinding
import com.jeflette.newsapi.ui.bookmark.BookmarkFragmentDirections
import com.jeflette.newsapi.ui.llistnews.ListFragmentDirections
import com.jeflette.newsapi.util.withDateFormat

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val results: MutableList<Articles> = ArrayList()
    fun setList(result: List<Articles>) {
        results.clear()
        results.addAll(result)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(news: Articles) {
            binding.newsTitle.text = news.title
            binding.newsSource.text = news.source?.name ?: ""
            binding.newsDate.text = news.publishedAt?.withDateFormat() ?: ""
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .error(R.color.black)
                .into(binding.newsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(results[position])
        holder.itemView.setOnClickListener {
            val actionListFragmentToDetailFragment =
                ListFragmentDirections.actionListFragmentToDetailFragment(
                    results[position]
                )

            val actionBookmarkFragmentToDetailFragment =
                BookmarkFragmentDirections.actionBookmarkFragmentToDetailFragment(
                    results[position]
                )
            findNavController(it).currentDestination?.id?.let { id ->
                when (id) {
                    R.id.listFragment -> findNavController(it).navigate(
                        actionListFragmentToDetailFragment
                    )
                    R.id.bookmarkFragment -> findNavController(it).navigate(
                        actionBookmarkFragmentToDetailFragment
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int = results.size
}