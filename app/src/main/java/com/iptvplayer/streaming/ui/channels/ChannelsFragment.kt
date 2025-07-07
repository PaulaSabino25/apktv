package com.iptvplayer.streaming.ui.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.iptvplayer.streaming.databinding.FragmentChannelsBinding

class ChannelsFragment : Fragment() {
    private var _binding: FragmentChannelsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChannelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        // Exemplo de lista de canais IPTV
        val demoChannels = listOf(
            Channel(
                name = "Canal 1",
                description = "Notícias 24h",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/6/6a/JavaScript-logo.png",
                streamUrl = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"
            ),
            Channel(
                name = "Canal 2",
                description = "Esportes ao vivo",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/4/47/React.svg",
                streamUrl = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
            )
        )
        binding.recyclerView.adapter = ChannelsAdapter(requireContext(), demoChannels)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ChannelsFragment()
    }
}
