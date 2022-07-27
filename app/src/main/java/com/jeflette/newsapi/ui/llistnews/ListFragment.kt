package com.jeflette.newsapi.ui.llistnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeflette.newsapi.data.entity.Articles
import com.jeflette.newsapi.databinding.FragmentListBinding
import com.jeflette.newsapi.ui.adapter.NewsAdapter
import com.jeflette.newsapi.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val newsAdapter = NewsAdapter()

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.apply {
                    getSearchNews(query!!)
                    isLoading.observe(viewLifecycleOwner) { loading ->
                        if (loading) {
                            binding.progressBar.isVisible = true
                        }
                    }
                    news.observe(viewLifecycleOwner) { news ->
                        val sorted = news?.articles?.sortedByDescending { it?.publishedAt }
                        newsAdapter.setList(sorted as List<Articles>)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.apply {
            rvNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(context)
            }
            viewModel.newsResponse.observe(viewLifecycleOwner) { news ->
                when (news) {
                    is Resource.Loading -> {
                        progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        progressBar.isVisible = false
                        news.data?.sortedByDescending { it.publishedAt }
                            ?.let { newsAdapter.setList(it) }
                    }
                    is Resource.Error -> {
                        progressBar.isVisible = false
                        Log.e("Error", news.error.toString())
                    }
                }

                val sorted = news.data?.sortedByDescending { it.publishedAt }
                sorted?.let { newsAdapter.setList(it) }
                progressBar.isVisible = news is Resource.Loading && news.data.isNullOrEmpty()
                tvEmpty.isVisible = news is Resource.Error && news.data.isNullOrEmpty()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}