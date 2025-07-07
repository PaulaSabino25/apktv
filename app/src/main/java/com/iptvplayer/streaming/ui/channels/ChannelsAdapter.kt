package com.iptvplayer.streaming.ui.channels

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iptvplayer.streaming.R
import com.iptvplayer.streaming.model.Channel
import com.iptvplayer.streaming.ui.player.PlayerActivity

class ChannelsAdapter(
    private val context: Context,
    private val channels: List<Channel>
) : RecyclerView.Adapter<ChannelsAdapter.ChannelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channels[position]
        holder.name.text = channel.name
        holder.description.text = channel.description
        Glide.with(context).load(channel.logoUrl).placeholder(R.mipmap.ic_launcher).into(holder.logo)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("STREAM_URL", channel.streamUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = channels.size

    class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.channelImageView)
        val name: TextView = itemView.findViewById(R.id.channelNameTextView)
        val description: TextView = itemView.findViewById(R.id.channelDescriptionTextView)
    }
}
