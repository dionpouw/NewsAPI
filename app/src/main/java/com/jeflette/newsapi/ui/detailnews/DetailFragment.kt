package com.jeflette.newsapi.ui.detailnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jeflette.newsapi.R
import com.jeflette.newsapi.databinding.FragmentDetailBinding
import com.jeflette.newsapi.databinding.FragmentListBinding

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
        binding.webViewNews.settings.javaScriptEnabled = true
        args.news.url?.let { binding.webViewNews.loadUrl(it) }
    }
}