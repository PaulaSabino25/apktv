package com.iptvplayer.streaming.ui.player

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.iptvplayer.streaming.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("STREAM_URL") ?: ""
        if (url.isNotBlank()) {
            initializePlayer(url)
        } else {
            binding.errorTextView.visibility = View.VISIBLE
            binding.retryButton.visibility = View.VISIBLE
        }

        binding.retryButton.setOnClickListener {
            if (url.isNotBlank()) {
                initializePlayer(url)
            }
        }
    }

    private fun initializePlayer(url: String) {
        binding.loadingProgressBar.visibility = View.VISIBLE
        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            binding.playerView.player = exoPlayer
            val mediaItem = MediaItem.fromUri(url)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            binding.loadingProgressBar.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}
