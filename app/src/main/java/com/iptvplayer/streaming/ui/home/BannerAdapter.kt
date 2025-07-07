package com.iptvplayer.streaming.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iptvplayer.streaming.R

data class Banner(val imageUrl: String, val title: String)

class BannerAdapter(private val banners: List<Banner>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }
    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = banners[position]
        Glide.with(holder.itemView).load(banner.imageUrl).into(holder.image)
        holder.title.text = banner.title
    }
    override fun getItemCount() = banners.size
    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.bannerImageView)
        val title: TextView = itemView.findViewById(R.id.bannerTitleTextView)
    }
}
