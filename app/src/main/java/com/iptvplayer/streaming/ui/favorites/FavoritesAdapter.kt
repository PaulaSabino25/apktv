package com.iptvplayer.streaming.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iptvplayer.streaming.R
import com.iptvplayer.streaming.databinding.ItemFavoriteChannelBinding
import com.iptvplayer.streaming.model.FavoriteChannel
import java.text.SimpleDateFormat
import java.util.*

class FavoritesAdapter(
    private val onChannelClick: (FavoriteChannel) -> Unit,
    private val onRemoveFavorite: (FavoriteChannel) -> Unit
) : ListAdapter<FavoriteChannel, FavoritesAdapter.FavoriteViewHolder>(FavoriteDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteChannelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class FavoriteViewHolder(
        private val binding: ItemFavoriteChannelBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(favorite: FavoriteChannel) {
            binding.apply {
                textViewChannelName.text = favorite.title
                textViewChannelGroup.text = favorite.group ?: "Sem grupo"
                
                // Added date
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                textViewAddedDate.text = "Adicionado em ${dateFormat.format(Date(favorite.addedAt))}"
                
                // Channel logo
                if (!favorite.logo.isNullOrEmpty()) {
                    Glide.with(root.context)
                        .load(favorite.logo)
                        .placeholder(R.drawable.ic_tv)
                        .error(R.drawable.ic_tv)
                        .into(imageViewChannelLogo)
                } else {
                    imageViewChannelLogo.setImageResource(R.drawable.ic_tv)
                }
                
                // Click listeners
                root.setOnClickListener {
                    onChannelClick(favorite)
                }
                
                buttonRemoveFavorite.setOnClickListener {
                    onRemoveFavorite(favorite)
                }
            }
        }
    }
    
    class FavoriteDiffCallback : DiffUtil.ItemCallback<FavoriteChannel>() {
        override fun areItemsTheSame(oldItem: FavoriteChannel, newItem: FavoriteChannel): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: FavoriteChannel, newItem: FavoriteChannel): Boolean {
            return oldItem == newItem
        }
    }
}
