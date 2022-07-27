package com.jeflette.newsapi.ui.detailnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.jeflette.newsapi.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val checkIfArgsHasBookmark = args.articles?.isBookmarked
        binding.apply {
            webViewNews.apply {
                clearCache(true)
                webViewClient = WebViewClient()
                settings.apply {
                    javaScriptEnabled = true
                    useWideViewPort = true
                    domStorageEnabled = true
                }
                args.articles?.url?.let {
                    loadUrl(it)
                }
            }

            if (checkIfArgsHasBookmark == true) {
                binding.fabAddBookmarked.visibility = View.INVISIBLE
            } else {
                binding.fabAddBookmarked.visibility = View.VISIBLE
            }

            fabAddBookmarked.setOnClickListener {
                args.articles?.let { article ->
                    viewModel.updateArticles(article, true)
                    Toast.makeText(context, "News is Bookmarked", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}