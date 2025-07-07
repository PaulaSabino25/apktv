package com.iptvplayer.streaming.repository

import android.content.Context
import com.iptvplayer.streaming.database.*
import com.iptvplayer.streaming.model.*
import com.iptvplayer.streaming.service.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.util.*

class IPTVRepository(context: Context) {
    
    private val database = IPTVDatabase.getDatabase(context)
    private val m3uParser = M3UParser()
    private val epgParser = EPGParser()
    
    // Server Management
    suspend fun addServer(server: ServerConfig) {
        val entity = ServerConfigEntity(
            id = server.id,
            name = server.name,
            m3uUrl = server.m3uUrl,
            epgUrl = server.epgUrl,
            username = server.username,
            password = server.password,
            userAgent = server.userAgent,
            httpReferrer = server.httpReferrer,
            isActive = server.isActive,
            priority = server.priority,
            lastSync = server.lastSync
        )
        database.serverConfigDao().insertServer(entity)
    }
    
    suspend fun getAllServers(): List<ServerConfig> {
        return database.serverConfigDao().getAllServers().map { entity ->
            ServerConfig(
                id = entity.id,
                name = entity.name,
                m3uUrl = entity.m3uUrl,
                epgUrl = entity.epgUrl,
                username = entity.username,
                password = entity.password,
                userAgent = entity.userAgent,
                httpReferrer = entity.httpReferrer,
                isActive = entity.isActive,
                priority = entity.priority,
                lastSync = entity.lastSync
            )
        }
    }
    
    suspend fun syncServerChannels(serverId: String): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val servers = getAllServers()
            val server = servers.find { it.id == serverId } ?: return@withContext Result.failure(Exception("Server not found"))
            
            // Parse M3U playlist
            val playlist = m3uParser.parseM3U(server.m3uUrl, server.userAgent)
            
            // Clear old channels for this server
            database.cachedChannelDao().deleteChannelsByServer(serverId)
            
            // Convert and cache new channels
            val channelEntities = playlist.entries.map { entry ->
                CachedChannelEntity(
                    id = generateChannelId(entry.url, entry.title),
                    serverId = serverId,
                    title = entry.title,
                    url = entry.url,
                    group = entry.group,
                    logo = entry.logo,
                    epgId = entry.epgId,
                    language = entry.language,
                    country = entry.country,
                    isRadio = entry.isRadio,
                    isAdult = entry.isAdult,
                    categories = JSONArray(entry.categories).toString(),
                    lastUpdated = System.currentTimeMillis()
                )
            }
            
            database.cachedChannelDao().insertChannels(channelEntities)
            database.serverConfigDao().updateLastSync(serverId, System.currentTimeMillis())
            
            Result.success(channelEntities.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getAllChannels(): List<M3UEntry> {
        val entities = database.cachedChannelDao().getChannelsByServer("") // Get all
        return entities.map { entity ->
            val categories = try {
                val jsonArray = JSONArray(entity.categories)
                (0 until jsonArray.length()).map { jsonArray.getString(it) }
            } catch (e: Exception) {
                emptyList()
            }
            
            M3UEntry(
                url = entity.url,
                title = entity.title,
                group = entity.group,
                logo = entity.logo,
                epgId = entity.epgId,
                language = entity.language,
                country = entity.country,
                isRadio = entity.isRadio,
                isAdult = entity.isAdult,
                categories = categories
            )
        }
    }
    
    suspend fun searchChannels(query: String): List<M3UEntry> {
        val entities = database.cachedChannelDao().searchChannels(query)
        return entities.map { entity ->
            M3UEntry(
                url = entity.url,
                title = entity.title,
                group = entity.group,
                logo = entity.logo,
                epgId = entity.epgId,
                language = entity.language,
                country = entity.country,
                isRadio = entity.isRadio,
                isAdult = entity.isAdult
            )
        }
    }
    
    suspend fun getChannelGroups(): List<String> {
        return database.cachedChannelDao().getAllGroups()
    }
    
    suspend fun getChannelsByGroup(group: String): List<M3UEntry> {
        val entities = database.cachedChannelDao().getChannelsByGroup(group)
        return entities.map { entity ->
            M3UEntry(
                url = entity.url,
                title = entity.title,
                group = entity.group,
                logo = entity.logo,
                epgId = entity.epgId,
                language = entity.language,
                country = entity.country,
                isRadio = entity.isRadio,
                isAdult = entity.isAdult
            )
        }
    }
    
    // Favorites Management
    suspend fun addToFavorites(channel: M3UEntry) {
        val favorite = FavoriteChannelEntity(
            id = generateChannelId(channel.url, channel.title),
            title = channel.title,
            url = channel.url,
            logo = channel.logo,
            group = channel.group,
            addedAt = System.currentTimeMillis()
        )
        database.favoriteChannelDao().insertFavorite(favorite)
    }
    
    suspend fun removeFromFavorites(channelId: String) {
        database.favoriteChannelDao().deleteFavoriteById(channelId)
    }
    
    suspend fun getFavoriteChannels(): List<FavoriteChannel> {
        return database.favoriteChannelDao().getAllFavorites().map { entity ->
            FavoriteChannel(
                id = entity.id,
                title = entity.title,
                url = entity.url,
                logo = entity.logo,
                group = entity.group,
                addedAt = entity.addedAt
            )
        }
    }
    
    suspend fun isFavorite(channelId: String): Boolean {
        return database.favoriteChannelDao().isFavorite(channelId)
    }
    
    // Watch History Management
    suspend fun addToHistory(channel: M3UEntry, duration: Long = 0, position: Long = 0) {
        val history = WatchHistoryEntity(
            id = generateChannelId(channel.url, channel.title),
            channelTitle = channel.title,
            channelUrl = channel.url,
            logo = channel.logo,
            watchedAt = System.currentTimeMillis(),
            duration = duration,
            lastPosition = position
        )
        database.watchHistoryDao().insertHistory(history)
    }
    
    suspend fun getWatchHistory(limit: Int = 50): List<WatchHistory> {
        return database.watchHistoryDao().getRecentHistory(limit).map { entity ->
            WatchHistory(
                id = entity.id,
                channelTitle = entity.channelTitle,
                channelUrl = entity.channelUrl,
                logo = entity.logo,
                watchedAt = entity.watchedAt,
                duration = entity.duration,
                lastPosition = entity.lastPosition
            )
        }
    }
    
    suspend fun getLastWatchedPosition(url: String): Long {
        return database.watchHistoryDao().getLastWatchedPosition(url)?.lastPosition ?: 0L
    }
    
    suspend fun clearHistory() {
        database.watchHistoryDao().clearAllHistory()
    }
    
    // EPG Management
    suspend fun loadEPGData(epgUrl: String): EPGData {
        return epgParser.parseEPG(epgUrl)
    }
    
    suspend fun getCurrentProgram(channelId: String, epgData: EPGData): EPGProgram? {
        return epgParser.getCurrentProgram(channelId, epgData)
    }
    
    suspend fun getNextPrograms(channelId: String, epgData: EPGData, count: Int = 5): List<EPGProgram> {
        return epgParser.getNextPrograms(channelId, epgData, count)
    }
    
    // Utility Functions
    private fun generateChannelId(url: String, title: String): String {
        return "${url.hashCode()}_${title.hashCode()}".replace("-", "")
    }
    
    suspend fun cleanupOldData() {
        val thirtyDaysAgo = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L)
        database.watchHistoryDao().deleteOldHistory(thirtyDaysAgo)
        database.cachedChannelDao().deleteOldChannels(thirtyDaysAgo)
    }
}
