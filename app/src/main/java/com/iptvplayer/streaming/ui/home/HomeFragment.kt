package com.iptvplayer.streaming.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iptvplayer.streaming.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Banners de exemplo
        val banners = listOf(
            Banner(
                imageUrl = "https://image.tmdb.org/t/p/original/9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg",
                title = "Filme em Destaque 1"
            ),
            Banner(
                imageUrl = "https://image.tmdb.org/t/p/original/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
                title = "Filme em Destaque 2"
            ),
            Banner(
                imageUrl = "https://image.tmdb.org/t/p/original/8YFL5QQVPy3AgrEQxNYVSgiPEbe.jpg",
                title = "Série em Destaque"
            )
        )
        val bannerAdapter = BannerAdapter(banners)
        binding.bannerViewPager.adapter = bannerAdapter

        // Indicador do carrossel
        com.google.android.material.tabs.TabLayoutMediator(
            binding.bannerIndicator, binding.bannerViewPager
        ) { _, _ -> }.attach()

        // Destaques de exemplo
        val highlights = listOf(
            Highlight(
                imageUrl = "https://image.tmdb.org/t/p/w500/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
                title = "Filme A"
            ),
            Highlight(
                imageUrl = "https://image.tmdb.org/t/p/w500/9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg",
                title = "Filme B"
            ),
            Highlight(
                imageUrl = "https://image.tmdb.org/t/p/w500/8YFL5QQVPy3AgrEQxNYVSgiPEbe.jpg",
                title = "Série X"
            ),
            Highlight(
                imageUrl = "https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                title = "Série Y"
            )
        )
        val highlightAdapter = HighlightAdapter(highlights)
        binding.highlightRecyclerView.adapter = highlightAdapter

        // Lançamentos de exemplo
        val releases = listOf(
            Highlight(
                imageUrl = "https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                title = "Lançamento 1"
            ),
            Highlight(
                imageUrl = "https://image.tmdb.org/t/p/w500/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
                title = "Lançamento 2"
            ),
            Highlight(
                imageUrl = "https://image.tmdb.org/t/p/w500/8YFL5QQVPy3AgrEQxNYVSgiPEbe.jpg",
                title = "Lançamento 3"
            )
        )
        val releasesAdapter = HighlightAdapter(releases)
        binding.releasesRecyclerView.adapter = releasesAdapter

        // Populares de exemplo
        val popular = listOf(
            Highlight(
                imageUrl = "https://image.tmdb.org/t/p/w500/9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg",
                title = "Popular 1"
            ),
            Highlight(
                imageUrl = "https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                title = "Popular 2"
            ),
            Highlight(
                imageUrl = "https://image.tmdb.org/t/p/w500/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
                title = "Popular 3"
            )
        )
        val popularAdapter = HighlightAdapter(popular)
        binding.popularRecyclerView.adapter = popularAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
