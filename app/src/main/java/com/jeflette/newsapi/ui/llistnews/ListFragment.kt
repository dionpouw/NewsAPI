package com.jeflette.newsapi.ui.llistnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeflette.newsapi.databinding.FragmentListBinding
import com.jeflette.newsapi.ui.adapter.NewsAdapter
import com.jeflette.newsapi.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
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
                    news.observe(viewLifecycleOwner) { news ->
                        val sorted = news?.articles?.sortedByDescending { it?.publishedAt }
                        newsAdapter.setList(sorted)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        viewModel.getNews()
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }

            news.observe(viewLifecycleOwner) {
                binding.rvNews.visibility = View.VISIBLE
                newsAdapter.setList(it?.articles)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}