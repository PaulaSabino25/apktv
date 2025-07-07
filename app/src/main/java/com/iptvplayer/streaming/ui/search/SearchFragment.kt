package com.iptvplayer.streaming.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.iptvplayer.streaming.databinding.FragmentSearchBinding
import com.iptvplayer.streaming.model.M3UEntry
import com.iptvplayer.streaming.repository.IPTVRepository
import com.iptvplayer.streaming.ui.channels.ChannelsAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var repository: IPTVRepository
    private lateinit var channelsAdapter: ChannelsAdapter
    private var searchJob: Job? = null
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        repository = IPTVRepository(requireContext())
        setupRecyclerView()
        setupSearchView()
        showInitialState()
    }
    
    private fun setupRecyclerView() {
        channelsAdapter = ChannelsAdapter { channel ->
            playChannel(channel)
        }
        
        binding.recyclerViewResults.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = channelsAdapter
        }
    }
    
    private fun setupSearchView() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { searchChannels(it) }
                    return true
                }
                
                override fun onQueryTextChange(newText: String?): Boolean {
                    searchJob?.cancel()
                    
                    if (newText.isNullOrBlank()) {
                        showInitialState()
                        return true
                    }
                    
                    if (newText.length >= 3) {
                        searchJob = lifecycleScope.launch {
                            delay(500) // Debounce
                            searchChannels(newText)
                        }
                    }
                    return true
                }
            })
            
            // Expand search view by default
            isIconified = false
            requestFocus()
        }
    }
    
    private fun showInitialState() {
        binding.apply {
            recyclerViewResults.visibility = View.GONE
            textViewEmpty.visibility = View.VISIBLE
            textViewEmpty.text = "Digite para pesquisar canais..."
            progressBar.visibility = View.GONE
        }
        channelsAdapter.submitList(emptyList())
    }
    
    private fun searchChannels(query: String) {
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                binding.textViewEmpty.visibility = View.GONE
                
                val results = repository.searchChannels(query)
                
                if (results.isEmpty()) {
                    binding.recyclerViewResults.visibility = View.GONE
                    binding.textViewEmpty.visibility = View.VISIBLE
                    binding.textViewEmpty.text = "Nenhum canal encontrado para '$query'"
                } else {
                    binding.recyclerViewResults.visibility = View.VISIBLE
                    binding.textViewEmpty.visibility = View.GONE
                    channelsAdapter.submitList(results)
                }
                
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Erro na pesquisa: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                
                binding.recyclerViewResults.visibility = View.GONE
                binding.textViewEmpty.visibility = View.VISIBLE
                binding.textViewEmpty.text = "Erro na pesquisa"
                
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    
    private fun playChannel(channel: M3UEntry) {
        // Add to history
        lifecycleScope.launch {
            repository.addToHistory(channel)
        }
        
        // Navigate to player
        // val intent = Intent(context, PlayerActivity::class.java)
        // intent.putExtra("channel_url", channel.url)
        // intent.putExtra("channel_title", channel.title)
        // startActivity(intent)
        
        Toast.makeText(context, "Reproduzindo: ${channel.title}", Toast.LENGTH_SHORT).show()
    }
    
    fun performSearch(query: String) {
        binding.searchView.setQuery(query, true)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        searchJob?.cancel()
        _binding = null
    }
}
