package com.iptvplayer.streaming.ui.favorites

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.iptvplayer.streaming.databinding.FragmentFavoritesBinding
import com.iptvplayer.streaming.model.FavoriteChannel
import com.iptvplayer.streaming.repository.IPTVRepository
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var repository: IPTVRepository
    private lateinit var favoritesAdapter: FavoritesAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        repository = IPTVRepository(requireContext())
        setupRecyclerView()
        setupSwipeRefresh()
        loadFavorites()
    }
    
    private fun setupRecyclerView() {
        favoritesAdapter = FavoritesAdapter(
            onChannelClick = { favorite ->
                playChannel(favorite)
            },
            onRemoveFavorite = { favorite ->
                showRemoveConfirmation(favorite)
            }
        )
        
        binding.recyclerViewFavorites.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = favoritesAdapter
        }
    }
    
    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadFavorites()
        }
    }
    
    private fun loadFavorites() {
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                val favorites = repository.getFavoriteChannels()
                
                if (favorites.isEmpty()) {
                    binding.recyclerViewFavorites.visibility = View.GONE
                    binding.layoutEmpty.visibility = View.VISIBLE
                } else {
                    binding.recyclerViewFavorites.visibility = View.VISIBLE
                    binding.layoutEmpty.visibility = View.GONE
                    favoritesAdapter.submitList(favorites)
                }
                
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Erro ao carregar favoritos: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }
    
    private fun playChannel(favorite: FavoriteChannel) {
        Toast.makeText(context, "Reproduzindo: ${favorite.title}", Toast.LENGTH_SHORT).show()
        
        // Navigate to player
        // val intent = Intent(context, PlayerActivity::class.java)
        // intent.putExtra("channel_url", favorite.url)
        // intent.putExtra("channel_title", favorite.title)
        // startActivity(intent)
    }
    
    private fun showRemoveConfirmation(favorite: FavoriteChannel) {
        AlertDialog.Builder(requireContext())
            .setTitle("Remover Favorito")
            .setMessage("Deseja remover '${favorite.title}' dos favoritos?")
            .setPositiveButton("Remover") { _, _ ->
                removeFavorite(favorite)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun removeFavorite(favorite: FavoriteChannel) {
        lifecycleScope.launch {
            try {
                repository.removeFromFavorites(favorite.id)
                loadFavorites()
                Toast.makeText(
                    context,
                    "Removido dos favoritos",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Erro ao remover favorito: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadFavorites()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
