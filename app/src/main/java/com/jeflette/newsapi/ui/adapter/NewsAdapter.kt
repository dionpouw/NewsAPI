package com.jeflette.newsapi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeflette.newsapi.R
import com.jeflette.newsapi.data.local.entity.Articles
import com.jeflette.newsapi.databinding.NewsItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val results: MutableList<Articles> = ArrayList()
    fun setList(result: List<Articles>) {
        results.clear()
        results.addAll(result)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bind = NewsItemBinding.bind(itemView)
        fun binding(news: Articles) {
            bind.newsTitle.text = news.title
            bind.newsSource.text = news.source?.name ?: ""
            bind.newsDate.text = news.publishedAt
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .into(bind.newsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(results[position])

        holder.itemView.setOnClickListener{
            // Find nav
        }
    }

    override fun getItemCount(): Int = results.size
}