package com.jeflette.newsapi.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeflette.newsapi.databinding.FragmentBookmarkBinding
import com.jeflette.newsapi.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val viewModel: BookmarkViewModel by viewModels()
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val newsAdapter = NewsAdapter()

        binding.apply {
            rvBookmarked.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(context)
            }
            viewModel.apply {
                getBookmarkNews()
                bookmarkedNews.observe(viewLifecycleOwner) { article ->
                    newsAdapter.setList(article.sortedByDescending { it.publishedAt })
                    tvEmpty.isVisible = article.isEmpty()
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

}