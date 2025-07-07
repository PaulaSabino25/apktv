package com.iptvplayer.streaming.ui.servers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iptvplayer.streaming.databinding.ItemServerBinding
import com.iptvplayer.streaming.model.ServerConfig
import java.text.SimpleDateFormat
import java.util.*

class ServersAdapter(
    private val onServerClick: (ServerConfig) -> Unit,
    private val onServerSync: (ServerConfig) -> Unit,
    private val onServerDelete: (ServerConfig) -> Unit
) : ListAdapter<ServerConfig, ServersAdapter.ServerViewHolder>(ServerDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {
        val binding = ItemServerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServerViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ServerViewHolder(
        private val binding: ItemServerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(server: ServerConfig) {
            binding.apply {
                textViewServerName.text = server.name
                textViewServerUrl.text = server.m3uUrl
                
                // Status indicator
                viewStatusIndicator.setBackgroundColor(
                    if (server.isActive) {
                        android.graphics.Color.GREEN
                    } else {
                        android.graphics.Color.RED
                    }
                )
                
                // Last sync info
                if (server.lastSync > 0) {
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    textViewLastSync.text = "Última sinc: ${dateFormat.format(Date(server.lastSync))}"
                } else {
                    textViewLastSync.text = "Nunca sincronizado"
                }
                
                // Click listeners
                root.setOnClickListener {
                    onServerClick(server)
                }
                
                buttonSync.setOnClickListener {
                    onServerSync(server)
                }
                
                buttonDelete.setOnClickListener {
                    onServerDelete(server)
                }
                
                // Server priority
                textViewPriority.text = "Prioridade: ${server.priority}"
            }
        }
    }
    
    class ServerDiffCallback : DiffUtil.ItemCallback<ServerConfig>() {
        override fun areItemsTheSame(oldItem: ServerConfig, newItem: ServerConfig): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: ServerConfig, newItem: ServerConfig): Boolean {
            return oldItem == newItem
        }
    }
}
