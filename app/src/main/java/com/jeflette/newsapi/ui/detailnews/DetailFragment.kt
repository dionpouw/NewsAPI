package com.jeflette.newsapi.ui.detailnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jeflette.newsapi.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
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
        binding.webViewNews.apply {
            clearCache(true)
            webChromeClient = WebChromeClient()
            settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
                domStorageEnabled = true
            }
            args.news.url?.let {
                loadUrl(it)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}