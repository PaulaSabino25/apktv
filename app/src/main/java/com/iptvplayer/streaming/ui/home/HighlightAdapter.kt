package com.iptvplayer.streaming.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iptvplayer.streaming.R

data class Highlight(val imageUrl: String, val title: String)

class HighlightAdapter(private val highlights: List<Highlight>) : RecyclerView.Adapter<HighlightAdapter.HighlightViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighlightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_highlight, parent, false)
        return HighlightViewHolder(view)
    }
    override fun onBindViewHolder(holder: HighlightViewHolder, position: Int) {
        val highlight = highlights[position]
        Glide.with(holder.itemView).load(highlight.imageUrl).into(holder.image)
        holder.title.text = highlight.title
    }
    override fun getItemCount() = highlights.size
    class HighlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.highlightImageView)
        val title: TextView = itemView.findViewById(R.id.highlightTitleTextView)
    }
}
