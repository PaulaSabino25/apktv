package com.iptvplayer.streaming.model

data class M3UEntry(
    val url: String,
    val title: String,
    val group: String? = null,
    val logo: String? = null,
    val epgId: String? = null,
    val language: String? = null,
    val country: String? = null,
    val isRadio: Boolean = false,
    val isAdult: Boolean = false,
    val catchup: String? = null,
    val userAgent: String? = null,
    val httpReferrer: String? = null,
    val categories: List<String> = emptyList()
)

data class M3UPlaylist(
    val entries: List<M3UEntry>,
    val headers: Map<String, String> = emptyMap(),
    val sourceUrl: String? = null,
    val lastUpdated: Long = System.currentTimeMillis()
)

data class EPGProgram(
    val channelId: String,
    val title: String,
    val description: String? = null,
    val startTime: Long,
    val endTime: Long,
    val category: String? = null,
    val icon: String? = null,
    val isLive: Boolean = false
)

data class EPGData(
    val channels: Map<String, List<EPGProgram>>,
    val lastUpdated: Long = System.currentTimeMillis()
)

data class FavoriteChannel(
    val id: String,
    val title: String,
    val url: String,
    val logo: String? = null,
    val group: String? = null,
    val addedAt: Long = System.currentTimeMillis()
)

data class WatchHistory(
    val id: String,
    val channelTitle: String,
    val channelUrl: String,
    val logo: String? = null,
    val watchedAt: Long,
    val duration: Long = 0,
    val lastPosition: Long = 0
)

data class ServerConfig(
    val id: String,
    val name: String,
    val m3uUrl: String,
    val epgUrl: String? = null,
    val username: String? = null,
    val password: String? = null,
    val userAgent: String? = null,
    val httpReferrer: String? = null,
    val isActive: Boolean = true,
    val priority: Int = 0,
    val lastSync: Long = 0
)
